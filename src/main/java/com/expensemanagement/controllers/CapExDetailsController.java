package com.expensemanagement.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensemanagement.models.ClientInformation;
import com.expensemanagement.services.interfaces.ClientInformationService;


	@RestController
	@RequestMapping("/clientInfo")
	public class CapExDetailsController {
		 private static final Logger LOGGER = LogManager.getLogger(ClientInfoController.class);
		@Autowired
		private ClientInformationService clientInformationService;

		@PostMapping("/createClientInfo")
		public ResponseEntity<ClientInformation> createClientinfo(@RequestBody ClientInformation clientInformation,HttpServletRequest request) {
			LOGGER.info("API Call From IP: " + request.getRemoteHost());
			ClientInformation clientInfo = clientInformationService.createClientInfo(clientInformation);
			return new ResponseEntity<>(clientInfo, HttpStatus.OK);
		}
}
