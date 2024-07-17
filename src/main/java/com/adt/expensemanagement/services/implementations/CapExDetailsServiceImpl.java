package com.adt.expensemanagement.services.implementations;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adt.expensemanagement.models.CapExDetails;
import com.adt.expensemanagement.repositories.CapExDetailsRepository;
import com.adt.expensemanagement.services.interfaces.CapExDetailsService;
import com.adt.expensemanagement.util.CapExDetailsUtility;
import com.adt.expensemanagement.util.TableDataExtractor;

@Service
public class CapExDetailsServiceImpl implements CapExDetailsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CapExDetailsRepository capExDetailsRepository;

	@Autowired
	private TableDataExtractor dataExtractor;

	@Override
	public CapExDetails createCapExDetails(MultipartFile invoice, CapExDetails capExDetails) {
		try {

			if (!CapExDetailsUtility.validateAmount(capExDetails.getAmount())) {
				throw new IllegalArgumentException("Invalid  Amount Details");
			}
			if (!CapExDetailsUtility.validateCapEx(capExDetails.getPaidBy())) {
				throw new IllegalArgumentException("Invalid Paid Details");
			}
			if (!CapExDetailsUtility.validateCapEx(capExDetails.getMode())) {
				throw new IllegalArgumentException("Invalid Mode Details");
			}
			if (!CapExDetailsUtility.validateCapEx(capExDetails.getComment())) {
				throw new IllegalArgumentException("Invalid Comment");
			}
			if (!CapExDetailsUtility.validateGST(capExDetails.getGstBill())) {
				throw new IllegalArgumentException("Invalid Bill Details");
			}
			if (!CapExDetailsUtility.validateExpense(capExDetails.getExpenseDetails())) {
				throw new IllegalArgumentException("Invalid Expense Details");
			}
			capExDetails.setInvoice(invoice.getBytes());
			return capExDetailsRepository.save(capExDetails);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getAllCapExDetails() {
		String sql = "SELECT * FROM expense_schema.cap_ex_details";
		List<Map<String, Object>> capExDetailsList = dataExtractor.extractDataFromTable(sql);
		List<Map<String, Object>> listOfCapExDetails = new ArrayList();
		for (Map<String, Object> capExDetails : capExDetailsList) {
			CapExDetails capExDetails2 = new CapExDetails();
			listOfCapExDetails.add(capExDetails);
		}
		return listOfCapExDetails;
	}

	@Override
	public CapExDetails getCapExDetailsById(int id) {
		return capExDetailsRepository.findById(id).orElse(null);
	}

	@Override
	public String updateCapExDetailsById(MultipartFile invoice, CapExDetails capExDetails) {
		int id = capExDetails.getId();
		CapExDetails capEx = capExDetailsRepository.findById(id).orElse(null);
		if (capEx != null) {
			capEx.setDate(capExDetails.getDate());
			capEx.setExpenseDetails(capExDetails.getExpenseDetails());
			capEx.setGstBill(capExDetails.getGstBill());
			capEx.setAmount(capExDetails.getAmount());
			capEx.setPaidBy(capExDetails.getPaidBy());
			capEx.setComment(capExDetails.getComment());
			capEx.setMode(capExDetails.getMode());
			if (invoice != null && !invoice.isEmpty()) {
				try {
					capEx.setInvoice(invoice.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return " Capital Expenses Updated Successfully for ID : " + capExDetailsRepository.save(capEx).getId();
		} else {
			return "Update Operation Faild";
		}
	}

	@Override
	public boolean deleteCapExDetailsById(int id) {
		Optional<CapExDetails> capExDetailsOptional = capExDetailsRepository.findById(id);
		if (capExDetailsOptional.isPresent()) {
			capExDetailsRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> searchCapExDetailsByDate(LocalDate fromDate, LocalDate toDate) throws Exception {
		log.info("CapExDetailsServiceImpl : searchCapExDetailsByDate info level log msg");
		List<Map<String, Object>> listOfCapExDetails = new ArrayList<>();
		try {
			validateFromDateAndToDate(fromDate, toDate);

			String sql = "SELECT * FROM expense_schema.cap_ex_details c WHERE c.date BETWEEN '" + fromDate + "' AND '"
					+ toDate + "'";

			List<Map<String, Object>> capExDetailsList = dataExtractor.extractDataFromTable(sql);

			if (capExDetailsList.isEmpty()) {
				throw new Exception("The CapExDetails between InputFromDate: " + fromDate + " to InputToDate: " + toDate
						+ " Not Found");
			}

			for (Map<String, Object> capExDetails : capExDetailsList) {
//				CapExDetails capExDetails2 = new CapExDetails();

				listOfCapExDetails.add(capExDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("CapExDetailsServiceImpl : searchCapExDetailsByDate error level log msg" + e.getMessage());
			throw e;
		}
		return listOfCapExDetails;
	}

	private void validateFromDateAndToDate(LocalDate fromDate, LocalDate toDate) throws IllegalArgumentException {
		LocalDate currentDate = LocalDate.now();

		if (fromDate.isAfter(currentDate) || toDate.isAfter(currentDate)) {
			throw new IllegalArgumentException("The InputFromDate: " + fromDate + " or InputToDate: " + toDate
					+ " cannot be greater than the CurrentDate: " + currentDate);
		}

		if (toDate.isBefore(fromDate)) {
			throw new IllegalArgumentException(
					"The InputToDate: " + toDate + " cannot be before the InputFromDate: " + fromDate);
		}
	}

}
