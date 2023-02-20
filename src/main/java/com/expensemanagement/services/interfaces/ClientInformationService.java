package com.expensemanagement.services.interfaces;

import com.expensemanagement.models.ClientInformation;

public interface ClientInformationService {

	public ClientInformation createClientInfo(ClientInformation clientInformation);

	public String updateClientInfo(int id, ClientInformation clientInformation);
}