package com.expensemanagement.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.models.ResponseModel;
import com.expensemanagement.repositories.ClientInformationRepo;
import com.expensemanagement.services.interfaces.ClientInformationService;

@Service
public class ClientInformationServiceImpl implements ClientInformationService{
	
	@Autowired
	private ClientInformationRepo informationRepo;
	
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

}
