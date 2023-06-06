package com.adt.expensemanagement.services.implementations;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.adt.expensemanagement.models.ClientInformation;
import com.adt.expensemanagement.models.ResponseModel;
import com.adt.expensemanagement.repositories.ClientInformationRepo;
import com.adt.expensemanagement.services.interfaces.ClientInformationService;

@Service
public class ClientInformationServiceImpl implements ClientInformationService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClientInformationRepo informationRepo;

	@Autowired
	private MessageSource messageSource;

	@Override
	public ResponseModel saveClientInfo(ClientInformation clientInfo) {
		informationRepo.save(clientInfo);
		ResponseModel responseModel = new ResponseModel();
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

	@Override
	public ClientInformation getClientInfoById(int clientId) {
		Optional<ClientInformation> opt = informationRepo.findById(clientId);
		if (!opt.isPresent())
			return null;

		return opt.get();
	}
}
