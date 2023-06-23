package com.adt.expensemanagement.services.interfaces;

import java.util.List;

import com.adt.expensemanagement.models.ClientInformation;
import com.adt.expensemanagement.models.ResponseModel;

public interface ClientInformationService {

	public ResponseModel saveClientInfo(ClientInformation clientInfo);
	public List<ClientInformation> getAllClientInfo();	
	public String updateClientInfo(int id, ClientInformation clientInformation); 
	//** HRMS-54 START Added new service interface method **
	public ClientInformation getClientInfoById(int clientId);
	//** HRMS-54 END **
	
	// ------------** Jira No. HRMS-84 START **-----------
	public List<ClientInformation> SearchByCompany(String companyName);
	public List<ClientInformation> SearchByEmail(String email);
	//------------ ** Jira No. HRMS-84 END **-------------

}


