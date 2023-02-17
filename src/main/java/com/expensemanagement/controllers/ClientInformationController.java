package com.expensemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.models.ResponseModel;
import com.expensemanagement.services.interfaces.ClientInformationService;

@RestController
public class ClientInformationController {
	
    @Autowired
    private ClientInformationService informationService;
	
    @PostMapping("/saveClientInfo")
    public ResponseEntity<ResponseModel> saveClientInfo(@RequestBody ClientInformation clientInfo) {
    	ResponseModel model=informationService.saveClientInfo(clientInfo);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
	
	@GetMapping("/getAllClientInfo")
	public ResponseEntity<List<ClientInformation>> getAllClientInfo() {
		List<ClientInformation> information=informationService.getAllClientInfo();
		return new ResponseEntity<>(information, HttpStatus.OK);
	}
    
}
