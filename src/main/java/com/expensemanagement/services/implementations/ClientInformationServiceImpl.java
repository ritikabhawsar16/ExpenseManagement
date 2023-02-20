package com.expensemanagement.services.implementations;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.expensemanagement.controllers.ClientInformationController;
import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.models.ResponseModel;
import com.expensemanagement.repositories.ClientInformationRepo;
import com.expensemanagement.services.interfaces.ClientInformationService;

@Service
public class ClientInformationServiceImpl implements ClientInformationService{
	
	@Autowired
	private ClientInformationRepo informationRepo;
	
    private static final Logger LOGGER = LogManager.getLogger(ClientInformationController.class);

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public ResponseModel saveClientInfo(ClientInformation clientInfo) {
 	informationRepo.save(clientInfo);
 	ResponseModel responseModel=new ResponseModel();
 	responseModel.setMsg("Success");
		return responseModel;
	}

	@Override
	public List<ClientInformation> getAllClientInfo() {
	     List<ClientInformation> list = informationRepo.findAll();
		return list;
	}
	
	@Override
	public String updateClientInfo(int id, ClientInformation clientInformation) {
		Optional<ClientInformation> clientInfo = informationRepo.findById(id);
		if (!clientInfo.isPresent()) {
			String message = messageSource.getMessage("api.error.data.not.found.id", null, Locale.ENGLISH);
			LOGGER.error(message = message + id);
			throw new EntityNotFoundException(message);
		}
		clientInfo.get().setAddress(clientInformation.getAddress());
		clientInfo.get().setCompanyName(clientInformation.getCompanyName());
		clientInfo.get().setContactPerson(clientInformation.getContactPerson());
		clientInfo.get().setEmail(clientInformation.getEmail());
		clientInfo.get().setGstin(clientInformation.getGstin());
		clientInfo.get().setPhone(clientInformation.getPhone());
		informationRepo.save(clientInfo.get());
		return "update successfully";
	}
}
