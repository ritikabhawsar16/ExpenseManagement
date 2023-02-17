package com.expensemanagement.services.interfaces;

import java.util.List;

import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.models.ResponseModel;

public interface ClientInformationService {

	ResponseModel saveClientInfo(ClientInformation clientInfo);

	List<ClientInformation> getAllClientInfo();

}
