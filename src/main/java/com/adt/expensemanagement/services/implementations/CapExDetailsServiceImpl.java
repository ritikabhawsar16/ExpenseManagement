package com.adt.expensemanagement.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.expensemanagement.models.CapExDetails;
import com.adt.expensemanagement.repositories.CapExDetailsRepository;
import com.adt.expensemanagement.services.interfaces.CapExDetailsService;
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
