package com.adt.expensemanagement.controllers;

import com.adt.expensemanagement.GSTConstants.GST_Constants;
import com.adt.expensemanagement.models.GSTInvoice;
import com.adt.expensemanagement.services.interfaces.GSTInvoiceService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@RequestMapping("/gst")
@RestController
public class GSTInvoiceController {

	private static final Logger logger = LoggerFactory.getLogger(GSTInvoiceController.class);
	@Autowired
	private GSTInvoiceService gstInvoiceService;

	@PreAuthorize("@auth.allow('SAVE_GST_DETAILS')")
	@PostMapping("/saveGSTDetails")
	public ResponseEntity<String> saveGSTDetails(@RequestBody GSTInvoice gstInvoice) {
		logger.info("Received request to save GST details: {}", gstInvoice);

		GSTInvoice invoice = gstInvoiceService.findByInvoiceNumber(gstInvoice.getInvoiceNumber());

		if (invoice != null) {
			String message = String.format("Invoice number '%s' already exists", gstInvoice.getInvoiceNumber());
			logger.warn(message);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					GST_Constants.DATA_ALREADY_EXIST + " FOR INVOICE NUMBER: " + gstInvoice.getInvoiceNumber());
		} else {
			String saved = gstInvoiceService.saveGSTDetails(gstInvoice);
			if (!saved.isEmpty()) {
				return ResponseEntity.ok(GST_Constants.GST_DATA_SAVED);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(GST_Constants.GST_DATA_NOT_SAVED);
			}
		}
	}


	@PreAuthorize("@auth.allow('GET_GST_DETAILS_BY_INVOICE_NUMBER')")
	@GetMapping("/displayGSTDetailsByInvoiceNumber/{invoiceNumber}")
	public GSTInvoice displayGSTByInvoiceNumber(@PathVariable("invoiceNumber") String invoiceNumber) {

		GSTInvoice invoice = gstInvoiceService.findByInvoiceNumber(invoiceNumber);
		if (invoice != null) {
			return invoice;
		} else {
			return null;
		}
	}

	@PreAuthorize("@auth.allow('GET_ALL_GST_DETAILS')")
	@GetMapping("/displayAllGSTDetails")
	public ResponseEntity<Page> displayAllGSTDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<GSTInvoice> gstInvoicesPage = gstInvoiceService.findAll(pageable);
		if (gstInvoicesPage != null) {
			return ResponseEntity.ok(gstInvoicesPage);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PreAuthorize("@auth.allow('GENERATE_GST_EXCEL_BY_INVOICE_NUMBER')")
	@GetMapping("/generateGSTsheetByInvoiceNumber/{invoiceNumber}")
	public ResponseEntity<byte[]> generate_GST_Excel_By_InvoiceNumber(
			@PathVariable("invoiceNumber") String invoiceNumber) throws IOException {
		logger.info("Received request to sgenerate_GST_Excel_By_Id {}", invoiceNumber);
		GSTInvoice gstInvoice = gstInvoiceService.findByInvoiceNumber(invoiceNumber);

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Invoice for " + invoiceNumber);
		Row headerRow = sheet.createRow(0);
		String[] colNames = { "Invoice Number", "FY", "Invoice Date", "GST Period", "Billing Period", "Customer ID",
				"To", "Taxable Amount", "TDS", "GST @ 18%", "Invoice Amount (INR)", "Receivable", "Amount Received",
				"Date Received", "Invoice Balance", "Status", "TDS Credited", "TDS Balance" };
		for (int i = 0; i < colNames.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(colNames[i]);
		}

		int rowNum = 2;
		Row row = null;

		Row dataRow = sheet.createRow(rowNum);
		dataRow.createCell(0).setCellValue(gstInvoice.getInvoiceNumber());
		dataRow.createCell(1).setCellValue(gstInvoice.getFy());
		dataRow.createCell(2).setCellValue(gstInvoice.getInvoiceDate());
		dataRow.createCell(3).setCellValue(gstInvoice.getGstPeriod());
		dataRow.createCell(4).setCellValue(gstInvoice.getBillingPeriod());
		dataRow.createCell(5).setCellValue(gstInvoice.getCustomerId());
		dataRow.createCell(6).setCellValue(gstInvoice.getPaidTo());
		dataRow.createCell(7).setCellValue(gstInvoice.getTaxableAmount().doubleValue());
//		dataRow.createCell(8).setCellValue(gstInvoice.getTds().doubleValue());
		dataRow.createCell(9).setCellValue(gstInvoice.getGst().doubleValue());
		dataRow.createCell(10).setCellValue(gstInvoice.getInvoiceAmount().doubleValue());
		dataRow.createCell(11).setCellValue(gstInvoice.getReceivable().doubleValue());
		dataRow.createCell(12).setCellValue(gstInvoice.getAmountReceived().doubleValue());
		dataRow.createCell(13).setCellValue(gstInvoice.getDateReceived());
		dataRow.createCell(14).setCellValue(gstInvoice.getInvoiceBalance().doubleValue());
		dataRow.createCell(15).setCellValue(gstInvoice.getStatus());
//		dataRow.createCell(16).setCellValue(gstInvoice.getTdsCredited().doubleValue());
//		dataRow.createCell(17).setCellValue(gstInvoice.getTdsBalance().doubleValue());

		for (int i = 0; i < colNames.length; i++) {
			Cell cell = headerRow.createCell(i);
			String headerText = "   " + colNames[i] + "   ";
			cell.setCellValue(headerText);
			int headerWidth = headerText.length() * 256 + 1000;
			sheet.setColumnWidth(i, headerWidth);
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		workbook.close();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "invoices.xlsx");
		return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('GENERATE_ECXEL_FOR_ALL_GST_DETAILS')")
	@GetMapping("/generateExcelForAllGSTDetails")
	public ResponseEntity<byte[]> generate_All_GST_Details_Excel() throws IOException {

		logger.info("Received request to generate_All_GST_Details_Excel");
		List<GSTInvoice> gstInvoiceList = gstInvoiceService.findAll();

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Invoice");

		Row headerRow = sheet.createRow(0);
		String[] colNames = { "Invoice Number", "FY", "Invoice Date", "GST Period", "Billing Period", "Customer ID",
				"To", "Taxable Amount", "TDS", "GST @ 18%", "Invoice Amount (INR)", "Receivable", "Amount Received",
				"Date Received", "Invoice Balance", "Status", "TDS Credited", "TDS Balance" };
		for (int i = 0; i < colNames.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(colNames[i]);
		}

		int rowNum = 2;
		Row row = null;

		for (GSTInvoice gstInvoice : gstInvoiceList) {
			Row dataRow = sheet.createRow(rowNum);
			dataRow.createCell(0).setCellValue(gstInvoice.getInvoiceNumber());
			dataRow.createCell(1).setCellValue(gstInvoice.getFy());
			dataRow.createCell(2).setCellValue(gstInvoice.getInvoiceDate());
			dataRow.createCell(3).setCellValue(gstInvoice.getGstPeriod());
			dataRow.createCell(4).setCellValue(gstInvoice.getBillingPeriod());
			dataRow.createCell(5).setCellValue(gstInvoice.getCustomerId());
			dataRow.createCell(6).setCellValue(gstInvoice.getPaidTo());
			dataRow.createCell(7).setCellValue(gstInvoice.getTaxableAmount().doubleValue());
//			dataRow.createCell(8).setCellValue(gstInvoice.getTds().doubleValue());
			dataRow.createCell(9).setCellValue(gstInvoice.getGst().doubleValue());
			dataRow.createCell(10).setCellValue(gstInvoice.getInvoiceAmount().doubleValue());
			dataRow.createCell(11).setCellValue(gstInvoice.getReceivable().doubleValue());
			dataRow.createCell(12).setCellValue(gstInvoice.getAmountReceived().doubleValue());
			dataRow.createCell(13).setCellValue(gstInvoice.getDateReceived());
			dataRow.createCell(14).setCellValue(gstInvoice.getInvoiceBalance().doubleValue());
			dataRow.createCell(15).setCellValue(gstInvoice.getStatus());
//			dataRow.createCell(16).setCellValue(gstInvoice.getTdsCredited().doubleValue());
//			dataRow.createCell(17).setCellValue(gstInvoice.getTdsBalance().doubleValue());
			rowNum++;
		}

		for (int i = 0; i < colNames.length; i++) {
			Cell cell = headerRow.createCell(i);
			String headerText = "   " + colNames[i] + "   ";
			cell.setCellValue(headerText);
			int headerWidth = headerText.length() * 256 + 1000;
			sheet.setColumnWidth(i, headerWidth);
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		workbook.close();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "invoices.xlsx");
		return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('UPDATE_GST_DETAILS_BY_INVOICE_NUMBER')")
	@PutMapping("/updateGSTDetailsByInvoiceNumber/{invoiceNumber}")
	public HttpEntity<String> updateGSTDetailsByInvoiceNumber(@PathVariable("invoiceNumber") String invoiceNumber,
			@RequestBody GSTInvoice updatedInvoice) {
		logger.info("Update request for InvoiceNumber '%s' requested " + invoiceNumber);
		GSTInvoice existingInvoice = gstInvoiceService.findByInvoiceNumber(invoiceNumber);
		if (existingInvoice != null) {

			existingInvoice.setFy(updatedInvoice.getFy());
			existingInvoice.setInvoiceDate(updatedInvoice.getInvoiceDate());
			existingInvoice.setGstPeriod(updatedInvoice.getGstPeriod());
			existingInvoice.setBillingPeriod(updatedInvoice.getBillingPeriod());
			existingInvoice.setCustomerId(updatedInvoice.getCustomerId());
			existingInvoice.setPaidTo(updatedInvoice.getPaidTo());
			existingInvoice.setTaxableAmount(updatedInvoice.getTaxableAmount());
//			existingInvoice.setTds(updatedInvoice.getTds());
			existingInvoice.setGst(updatedInvoice.getGst());
			existingInvoice.setInvoiceAmount(updatedInvoice.getInvoiceAmount());
			existingInvoice.setReceivable(updatedInvoice.getReceivable());
			existingInvoice.setAmountReceived(updatedInvoice.getAmountReceived());
			existingInvoice.setDateReceived(updatedInvoice.getDateReceived());
			existingInvoice.setInvoiceBalance(updatedInvoice.getInvoiceBalance());
//			existingInvoice.setStatus(updatedInvoice.getStatus());
			existingInvoice.setStatus("Completed");
//			existingInvoice.setTdsCredited(updatedInvoice.getTdsCredited());
//			existingInvoice.setTdsBalance(updatedInvoice.getTdsBalance());

			String updated = gstInvoiceService.updateGSTDetailsByInvoiceNumber(existingInvoice);
			if (updated != null || !updated.equalsIgnoreCase("")) {
				return ResponseEntity.ok(GST_Constants.GST_DATA_UPDATED);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(GST_Constants.GST_DATA_NOT_SAVED);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(GST_Constants.INVOICE_NOT_FOUND + " " + invoiceNumber);
		}
	}

	@PreAuthorize("@auth.allow('DELETE_GST_INVOICE_BY_INVOICE_NUMBER')")
	@DeleteMapping("/deleteGSTInvoiceByInvoiceNumber/{invoiceNumber}")
	public ResponseEntity<String> deleteGSTInvoiceByInvoiceNumber(@PathVariable("invoiceNumber") String invoiceNumber) {
		logger.info("Delete GST details requested for Invoice Number {} ", invoiceNumber);
		int deleted = gstInvoiceService.deleteByInvoiceNumber(invoiceNumber);
		if (deleted != 0) {
			return ResponseEntity.status(HttpStatus.OK).body(GST_Constants.GST_DATA_DELETED);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(GST_Constants.INVOICE_NOT_FOUND + " " + invoiceNumber);
		}
	}

	private String generateInvoiceNumber() {
		int randomPart = new SecureRandom().nextInt(1000);
		String invoicePart = String.format("%03d", randomPart);
		String prefix = "AD" + String.format("%04d", new SecureRandom().nextInt(10000));
		return prefix + "-" + invoicePart;
	}

}
