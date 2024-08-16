package com.adt.expensemanagement.services.implementations;


import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import com.adt.expensemanagement.util.ExpenseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.adt.expensemanagement.models.ExpenseItems;
import com.adt.expensemanagement.models.ExpenseOutbound;
import com.adt.expensemanagement.repositories.ExpenseRepository;
import com.adt.expensemanagement.repositories.OutboundExpenseRepo;
import com.adt.expensemanagement.services.interfaces.ExpenseService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private OutboundExpenseRepo outboundExpenseRepo;

	@Override
	public ExpenseItems createExpenses(ExpenseItems expenseItems) {

		if (!ExpenseUtility.validateAmountAndId(expenseItems.getEmpId())) {
			throw new IllegalArgumentException("Invalid Employee ID");
		}
		if (!ExpenseUtility.validateAmountAndId(expenseItems.getAmount())) {
			throw new IllegalArgumentException("Invalid Amount Details");
		}
		if (!ExpenseUtility.validateDescription(expenseItems.getDescription())) {
			throw new IllegalArgumentException("Invalid Descriptions Details");
		}
		if (!ExpenseUtility.validateExpenses(expenseItems.getPaymentMode())) {
			throw new IllegalArgumentException("Invalid Payment Details");
		}

		if (!ExpenseUtility.validateExpenses(expenseItems.getCategory())) {
			throw new IllegalArgumentException("Invalid Category Details");
		}

		if (!ExpenseUtility.validateExpenses(expenseItems.getComments())) {
			throw new IllegalArgumentException("Invalid Output");
		}

		return expenseRepository.save(expenseItems);
	}

	@Override
	public String updateExpense(int id, ExpenseItems expenseModel) {

		Optional<ExpenseItems> exModel = expenseRepository.findById(id);

		if (!exModel.isPresent()) {
			String message = messageSource.getMessage("api.error.data.not.found.id", null, Locale.ENGLISH);
			LOGGER.error(message = message + id);
			throw new EntityNotFoundException(message);
		}
		exModel.get().setEmpId(expenseModel.getEmpId());
		exModel.get().setAmount(expenseModel.getAmount());
		exModel.get().setStatus(expenseModel.getStatus());
		//exModel.get().setCreatedBy(expenseModel.getCreatedBy());
		exModel.get().setDescription(expenseModel.getDescription());
		exModel.get().setPaymentDate(expenseModel.getPaymentDate());
		exModel.get().setPaymentMode(expenseModel.getPaymentMode());
		exModel.get().setGst(expenseModel.isGst());
//		exModel.get().setPaidBy(expenseModel.getPaidBy());
		exModel.get().setComments(expenseModel.getComments());
		expenseRepository.save(exModel.get());
		return "update successfully";

	}

	@Override
	public ExpenseItems getExpenseById(int id) throws NoSuchFieldException {
		Optional<ExpenseItems> expenseItems = expenseRepository.findById(id);
		if (!expenseItems.isPresent()) {
			String message = messageSource.getMessage("api.error.data.not.found.id", null, Locale.ENGLISH);
			LOGGER.error(message = message + id);
			throw new EntityNotFoundException(message);
		}
		return expenseItems.get();
	}

	@Override
	public String saveOutboundExpense(ExpenseOutbound expenseOutbound) {
		outboundExpenseRepo.save(expenseOutbound);
		return "Saved Successfully id is :" + expenseOutbound.getExpenseId();
	}

	@Override
	public String updateOutboundExpense(ExpenseOutbound expenseOutbound) {
		// TODO Auto-generated method stub
		Optional<ExpenseOutbound> outboundExp = outboundExpenseRepo.findById(expenseOutbound.getExpenseId());
		if (!outboundExp.isPresent()) {
			String message = messageSource.getMessage("api.error.data.not.found.id", null, Locale.ENGLISH);
			LOGGER.error(message = message + expenseOutbound.getExpenseId());
			throw new EntityNotFoundException(message);
		}
		outboundExp.get().setBalance(expenseOutbound.getBalance());
		outboundExp.get().setBillingPeriod(expenseOutbound.getBillingPeriod());
		outboundExp.get().setChargedAmount(expenseOutbound.getChargedAmount());
		outboundExp.get().setCustomerId(expenseOutbound.getCustomerId());
		outboundExp.get().setDate(expenseOutbound.getDate());
		outboundExp.get().setDateRecieved(expenseOutbound.getDateRecieved());
		outboundExp.get().setGst(expenseOutbound.getGst());
		outboundExp.get().setGstPeriod(expenseOutbound.getGstPeriod());
		outboundExp.get().setInrRecievable(expenseOutbound.getInrRecievable());
		outboundExp.get().setInrRecieved(expenseOutbound.getInrRecieved());
		outboundExp.get().setInvoiceAmtInr(expenseOutbound.getInvoiceAmtInr());
		outboundExp.get().setMode(expenseOutbound.getMode());
		outboundExp.get().setTds(expenseOutbound.getTds());

		outboundExpenseRepo.save(outboundExp.get());
		return "Successfully Updated id is :" + expenseOutbound.getExpenseId();
	}

	@Override
	public List<ExpenseItems> getAllExpenses() {
		List<ExpenseItems> expenseItemsList = expenseRepository.findAll();
		return expenseItemsList;
	}

	@Override
	public List<ExpenseItems> getExpenseByDateRange(String from, String to) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		LocalDate startDate = LocalDate.parse(from, formatter);
		LocalDate endDate = LocalDate.parse(to, formatter);
		Date utilDate = new Date();
		Instant instant = utilDate.toInstant();
		LocalDate currentDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		if (startDate.isAfter(endDate) || currentDate.isBefore(endDate) || daysBetween > 214) {
			String message = messageSource.getMessage("api.error.data.not.found", null, Locale.ENGLISH);
			LOGGER.error(message = message + " fromDate : " + startDate + " To : " + endDate);
			throw new IllegalArgumentException(message);
		}
		List<ExpenseItems> expenseItemsList = expenseRepository.getExpenseByDateRange(startDate, endDate);
		return expenseItemsList;
	}

	@Override
	public String deleteAllExpenseByIds(List<Integer> ids) {
		expenseRepository.deleteAllById(ids);
		return "Data Deleted Successfully";
	}

	@Override
	public String deleteExpenseById(int id) {
		Optional<ExpenseItems> expenseData = expenseRepository.findById(id);
		if (expenseData.isPresent()) {
			expenseRepository.deleteById(id);
			return "Data Deleted Successfully";
		}
		return "No data found for selected id";
	}
}

