package com.expensemanagement.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@PutMapping("/updateClientInfo/{id}")
	public ResponseEntity<String> updateClientInfo(@PathVariable("id") int id,
			@RequestBody ClientInformation clientInformation,HttpServletRequest request) {
		 LOGGER.info("API Call From IP: " + request.getRemoteHost());
		return new ResponseEntity<>(informationService.updateClientInfo(id, clientInformation),
				HttpStatus.OK);
	}
	//***** HRMS-54 START Added new handler method to get Client information using client id  *****
	@GetMapping("/getClientInfoById/{clientId}")
	public ResponseEntity<ClientInformation> getClientInformationById(@PathVariable("clientId") int clientId){
		LOGGER.info("Expense Management: ClientInformationController: getClientInformationById info level log message");
		ClientInformation clientInfo = informationService.getClientInfoById(clientId);
		if(clientInfo == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(clientInfo);
	}
	//***** HRMS-54 END ***
}
