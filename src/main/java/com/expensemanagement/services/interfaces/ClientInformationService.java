package com.expensemanagement.services.interfaces;

<<<<<<< HEAD
import java.util.List;

import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.models.ResponseModel;

public interface ClientInformationService {

	ResponseModel saveClientInfo(ClientInformation clientInfo);

	List<ClientInformation> getAllClientInfo();

}
=======
import com.expensemanagement.models.ClientInformation;

public interface ClientInformationService {

	public ClientInformation createClientInfo(ClientInformation clientInformation);

	public String updateClientInfo(int id, ClientInformation clientInformation);
}
>>>>>>> 42c5eaae09df00cf9d81c1d50684871d64b07cc7
