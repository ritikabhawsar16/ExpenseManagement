package com.adt.expensemanagement.services.implementations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adt.expensemanagement.models.GSTInvoice;
import com.adt.expensemanagement.repositories.GSTInvoiceRepository;
import com.adt.expensemanagement.services.interfaces.GSTInvoiceService;
import com.adt.expensemanagement.util.GSTInvoiceUtility;

@Service
public class GSTInvoiceServiceImpl implements GSTInvoiceService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GSTInvoiceRepository invoiceRepository;

	@Override
	public String saveGSTDetails(GSTInvoice gstInvoice) {
		log.info("GSTInvoiceServiceImpl:saveGSTDetails:info level log message");
		try {
			if (!GSTInvoiceUtility.checkValidate(gstInvoice.getInvoiceNumber())) {
				throw new IllegalArgumentException("Invalid GST Number");
			}
			if (!GSTInvoiceUtility.validatePeriod(gstInvoice.getFy())) {
				throw new IllegalArgumentException("Invalid Details of Financial Year");
			}
			if (!GSTInvoiceUtility.checkValidate(gstInvoice.getGstPeriod())) {
				throw new IllegalArgumentException("Invalid GST Period");
			}
			if (!GSTInvoiceUtility.checkValidate(gstInvoice.getBillingPeriod())) {
				throw new IllegalArgumentException("Invalid Billing Period");
			}
			if (!GSTInvoiceUtility.checkValidate(gstInvoice.getCustomerId())) {
				throw new IllegalArgumentException("Invalid Customer Id");
			}
			if (!GSTInvoiceUtility.validateString(gstInvoice.getPaidTo())) {
				throw new IllegalArgumentException("Invalid Paid details");
			}
			BigDecimal taxableAmount = new BigDecimal(String.valueOf(gstInvoice.getTaxableAmount())).setScale(2);
			if (GSTInvoiceUtility.validateBigDecimal(taxableAmount)) {
				throw new IllegalArgumentException("Invalid Tax Amount");
			}
			BigDecimal tds = new BigDecimal(String.valueOf(gstInvoice.getTds())).setScale(2);
			if (GSTInvoiceUtility.validateBigDecimal(tds)) {
				throw new IllegalArgumentException("Invalid TDS amount");
			}
			BigDecimal gst = new BigDecimal(String.valueOf(gstInvoice.getGst())).setScale(2);
			if (GSTInvoiceUtility.validateBigDecimal(gst)) {
				throw new IllegalArgumentException("Invalid GST Amount");
			}
			BigDecimal invoiceAmount = new BigDecimal(String.valueOf(gstInvoice.getInvoiceAmount())).setScale(2);
			if (GSTInvoiceUtility.validateBigDecimal(invoiceAmount)) {
				throw new IllegalArgumentException("Invalid Invoice Amount");
			}
			BigDecimal receivable = new BigDecimal(String.valueOf(gstInvoice.getReceivable())).setScale(2);
			if (GSTInvoiceUtility.validateBigDecimal(receivable)) {
				throw new IllegalArgumentException("Invalid Receivable Amount");
			}
			BigDecimal amountReceived = gstInvoice.getAmountReceived();
			BigDecimal invoiceBalance = gstInvoice.getInvoiceBalance();
			BigDecimal tdsCredited = gstInvoice.getTdsCredited();
			BigDecimal tdsBalance = gstInvoice.getTdsBalance();
			Date dateReceived = gstInvoice.getDateReceived();

			gstInvoice.setStatus("Pending");
			gstInvoice.setAmountReceived(amountReceived);
			gstInvoice.setInvoiceBalance(invoiceBalance);
			gstInvoice.setTdsCredited(tdsCredited);
			gstInvoice.setTdsBalance(tdsBalance);
			gstInvoice.setDateReceived(dateReceived);
			invoiceRepository.save(gstInvoice);

		} catch (Exception e) {
			log.error("saveGSTDetails Exception : " + e);
			e.printStackTrace();
			return e.getMessage();
		}
		return "GST DATA SAVED SUCCESSFULLY";
	}

	@Override
	public GSTInvoice findById(Long id) {
		log.info("GSTInvoiceServiceImpl:findById:info level log message");
		try {
			GSTInvoice invoice = invoiceRepository.findById((id)).orElse(null);
			return invoice;
		} catch (Exception e) {
			log.error("findById Exception : " + e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GSTInvoice> findAll() {
		log.info("GSTInvoiceServiceImpl:findAll:info level log message");

		try {
			List<GSTInvoice> gstInvoiceList = invoiceRepository.findAll();
			return gstInvoiceList;
		} catch (Exception e) {
			log.error("findAll Exception : " + e);
			e.printStackTrace();
		}
		return null;
	}

	public Page<GSTInvoice> findAll(Pageable pageable) {
		log.info("GSTInvoiceServiceImpl:findAll with Pagination:info level log message");

		try {
			return invoiceRepository.findAll(pageable);
		} catch (Exception e) {

			log.error("findAll Exception : " + e);
			e.printStackTrace();
		}
		return null;
	}

	public String updateGSTDetailsByInvoiceNumber(GSTInvoice invoice) {
		log.info("GSTInvoiceServiceImpl:update:info level log message");

		try {
			invoiceRepository.save(invoice);
			return "GST DATA UPDATED SUCCESSFULLY";
		} catch (Exception e) {

			log.error("update Exception : " + e);
			e.printStackTrace();
			return e.getMessage();
		}

	}

	@Override
	public GSTInvoice findByInvoiceNumber(String invoiceNumber) {
		log.info("GSTInvoiceServiceImpl:findByInvoiceNumber:info level log message");

		try {
			return invoiceRepository.findByInvoiceNumber(invoiceNumber);
		} catch (Exception e) {
			log.error("findByInvoiceNumber Exception : " + e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int deleteByInvoiceNumber(String invoiceNumber) {
		log.info("GSTInvoiceServiceImpl:deleteByInvoiceNumber:info level log message");
		try {
			int result = invoiceRepository.deleteByInvoiceNumber(invoiceNumber);
			if (result != 0)
				return 1;
			else
				return 0;
		} catch (Exception e) {
			log.error("findByInvoiceNumber Exception : " + e);
			e.printStackTrace();
		}
		return 0;

	}

}
