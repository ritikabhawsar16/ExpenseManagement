package com.expensemanagement.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensemanagement.models.CapExDetails;
import com.expensemanagement.repositories.CapExDetailsRepository;
import com.expensemanagement.services.interfaces.CapExDetailsService;
@Service
public class CapExDetailsServiceImpl implements CapExDetailsService {
	
	@Autowired
	private CapExDetailsRepository capExDetailsRepository;

	@Override
	public CapExDetails createCapExDetails(CapExDetails capExDetails) {
		CapExDetails capExDetail=capExDetailsRepository.save(capExDetails);
		return capExDetail;
	}

}
