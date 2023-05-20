package com.expensemanagement.services.interfaces;

import java.util.List;

import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.models.ResponseModel;

public interface ClientInformationService {

	public ResponseModel saveClientInfo(ClientInformation clientInfo);
	public List<ClientInformation> getAllClientInfo();	
	public String updateClientInfo(int id, ClientInformation clientInformation); 
	//** HRMS-54 START Added new service interface method **
	public ClientInformation getClientInfoById(int clientId);
	//** HRMS-54 END **
}


