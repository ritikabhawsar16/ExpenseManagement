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
import com.expensemanagement.models.CapExDetails;
import com.expensemanagement.services.interfaces.CapExDetailsService;

@RestController
@RequestMapping("/capExDetails")
public class CapExDetailsController {
	
	private static final Logger LOGGER = LogManager.getLogger(CapExDetailsController.class);
	
	@Autowired
	private CapExDetailsService capExDetailsService;

	@PostMapping("/createCapExDetails")
	public ResponseEntity<CapExDetails> createCapExDetails(@RequestBody CapExDetails capExDetails,
			HttpServletRequest request) {
		LOGGER.info("API Call From IP: " + request.getRemoteHost());
		CapExDetails details = capExDetailsService.createCapExDetails(capExDetails);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}
}
