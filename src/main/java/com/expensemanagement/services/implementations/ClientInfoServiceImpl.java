package com.expensemanagement.services.implementations;

import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.repositories.ClientInformationRepository;
import com.expensemanagement.services.interfaces.ClientInformationService;

@Service
class ClientInfoServiceImpl implements ClientInformationService {
	private static final Logger LOGGER = LogManager.getLogger(ClientInfoServiceImpl.class);
	@Autowired
	private ClientInformationRepository clientInformationRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public ClientInformation createClientInfo(ClientInformation clientInformation) {
		ClientInformation clientInfo = clientInformationRepository.save(clientInformation);
		return clientInfo;
	}

	@Override
	public String updateClientInfo(int id, ClientInformation clientInformation) {
		Optional<ClientInformation> clientInfo = clientInformationRepository.findById(id);
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
		clientInformationRepository.save(clientInfo.get());
		return "update successfully";
	}

}
