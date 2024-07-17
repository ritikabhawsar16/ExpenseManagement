package com.adt.expensemanagement.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.adt.expensemanagement.models.CapExDetails;

public interface CapExDetailsService {

	public CapExDetails createCapExDetails(MultipartFile invoice, CapExDetails capExDetails);

	List<Map<String, Object>> getAllCapExDetails();

	CapExDetails getCapExDetailsById(int id);

	String updateCapExDetailsById(MultipartFile invoice, CapExDetails capExDetails);

	boolean deleteCapExDetailsById(int id);

	public List<Map<String, Object>> searchCapExDetailsByDate(LocalDate fromDate, LocalDate toDate) throws Exception;

}
