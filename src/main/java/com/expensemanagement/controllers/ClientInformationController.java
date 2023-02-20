package com.expensemanagement.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.models.ResponseModel;
import com.expensemanagement.services.interfaces.ClientInformationService;

@RestController
@RequestMapping("/clientInfo")
public class ClientInformationController {
	
    @Autowired
    private ClientInformationService informationService;
	
    private static final Logger LOGGER = LogManager.getLogger(ClientInformationController.class);

    
    @PostMapping("/saveClientInfo")
    public ResponseEntity<ResponseModel> saveClientInfo(@RequestBody ClientInformation clientInfo) {
        LOGGER.info("Expense Management: clientInfo : saveClientInfo Info level log message");

    	ResponseModel model=informationService.saveClientInfo(clientInfo);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
	
	@GetMapping("/getAllClientInfo")
	public ResponseEntity<List<ClientInformation>> getAllClientInfo() {
        LOGGER.info("Expense Management: getAllClientInfo : getAllClientInfo Info level log message");
		
		List<ClientInformation> information=informationService.getAllClientInfo();
		return new ResponseEntity<>(information, HttpStatus.OK);
	}
    
}
