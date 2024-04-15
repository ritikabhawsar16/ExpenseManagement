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
import java.util.List;
import java.util.Random;

@RequestMapping("/gst")
@RestController
public class GSTInvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(GSTInvoiceController.class);
    @Autowired
    private GSTInvoiceService gstInvoiceService;

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @PostMapping("/saveGSTDetails")
    public ResponseEntity<String> saveGSTDetails(@RequestBody GSTInvoice gstInvoice) {

        logger.info("Received request to save GST details: {}", gstInvoice);

        List<GSTInvoice> resultList = gstInvoiceService.findByInvoiceNumberOrCustomerId(gstInvoice.getInvoiceNumber(), gstInvoice.getCustomerId());

        if (!resultList.isEmpty()) {

            for (GSTInvoice invoice : resultList) {
                String invoiceNumber = invoice.getInvoiceNumber();
                String customerID = invoice.getCustomerId();
                if (invoiceNumber.equals(gstInvoice.getInvoiceNumber()) && customerID.equals(gstInvoice.getCustomerId())) {
                    String message = String.format("Invoice number '%s' and Customer ID '%s' already exists ", gstInvoice.getInvoiceNumber(), gstInvoice.getCustomerId());
                    logger.warn(message);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GST_Constants.DATA_ALREADY_EXIST + " FOR INVOICE NUMBER: " + gstInvoice.getInvoiceNumber() + " AND FOR CUSTOMER ID: " + gstInvoice.getCustomerId());
                } else if (customerID.equals(gstInvoice.getCustomerId())) {
                    String message = String.format("Customer ID '%s' already exists ", gstInvoice.getCustomerId());
                    logger.warn(message);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GST_Constants.DATA_ALREADY_EXIST + " FOR CUSTOMER ID: " + gstInvoice.getCustomerId());
                } else if (invoiceNumber.equals(gstInvoice.getInvoiceNumber())) {
                    String message = String.format("Invoice number '%s' already exists ", gstInvoice.getInvoiceNumber());
                    logger.warn(message);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GST_Constants.DATA_ALREADY_EXIST + " FOR INVOICE NUMBER: " + gstInvoice.getInvoiceNumber());
                }
            }

        } else {
            gstInvoiceService.save(gstInvoice);
            return ResponseEntity.status(HttpStatus.OK).body(GST_Constants.GST_DATA_SAVED);
        }
        return null;
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/displayGSTDetailsById/{id}")
    public GSTInvoice displayGSTById(@PathVariable("id") Long id) {

        GSTInvoice invoice = gstInvoiceService.findById(id);
        if (invoice != null) {
            return invoice;
        } else {
            return null;
        }
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/displayAllGSTDetails")
    public ResponseEntity<Page> displayAllGSTDetails(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GSTInvoice> gstInvoicesPage = gstInvoiceService.findAll(pageable);
        if (gstInvoicesPage != null) {
            return ResponseEntity.ok(gstInvoicesPage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/generateGSTsheetById/{id}")
    public ResponseEntity<byte[]> generate_GST_Excel_By_Id(@PathVariable("id") Long id) throws IOException {
        logger.info("Received request to sgenerate_GST_Excel_By_Id {}", id);
        GSTInvoice gstInvoice = gstInvoiceService.findById(id);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoices");
        Row headerRow = sheet.createRow(0);
        String[] colNames = {"Invoice Number", "FY", "Invoice Date", "GST Period", "Billing Period", "Customer ID", "To", "Taxable Amount", "TDS", "GST @ 18%", "Invoice Amount (INR)", "Receivable", "Amount Received", "Date Received", "Invoice Balance", "Status", "TDS Credited", "TDS Balance"};
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
        dataRow.createCell(8).setCellValue(gstInvoice.getTds().doubleValue());
        dataRow.createCell(9).setCellValue(gstInvoice.getGst().doubleValue());
        dataRow.createCell(10).setCellValue(gstInvoice.getInvoiceAmount().doubleValue());
        dataRow.createCell(11).setCellValue(gstInvoice.getReceivable().doubleValue());
        dataRow.createCell(12).setCellValue(gstInvoice.getAmountReceived().doubleValue());
        dataRow.createCell(13).setCellValue(gstInvoice.getDateReceived());
        dataRow.createCell(14).setCellValue(gstInvoice.getInvoiceBalance().doubleValue());
        dataRow.createCell(15).setCellValue(gstInvoice.getStatus());
        dataRow.createCell(16).setCellValue(gstInvoice.getTdsCredited().doubleValue());
        dataRow.createCell(17).setCellValue(gstInvoice.getTdsBalance().doubleValue());

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

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/generateExcelForAllGSTDetails")
    public ResponseEntity<byte[]> generate_All_GST_Details_Excel() throws IOException {

        logger.info("Received request to generate_All_GST_Details_Excel");
        List<GSTInvoice> gstInvoiceList = gstInvoiceService.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoices");

        Row headerRow = sheet.createRow(0);
        String[] colNames = {"Invoice Number", "FY", "Invoice Date", "GST Period", "Billing Period", "Customer ID", "To", "Taxable Amount", "TDS", "GST @ 18%", "Invoice Amount (INR)", "Receivable", "Amount Received", "Date Received", "Invoice Balance", "Status", "TDS Credited", "TDS Balance"};
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
            dataRow.createCell(8).setCellValue(gstInvoice.getTds().doubleValue());
            dataRow.createCell(9).setCellValue(gstInvoice.getGst().doubleValue());
            dataRow.createCell(10).setCellValue(gstInvoice.getInvoiceAmount().doubleValue());
            dataRow.createCell(11).setCellValue(gstInvoice.getReceivable().doubleValue());
            dataRow.createCell(12).setCellValue(gstInvoice.getAmountReceived().doubleValue());
            dataRow.createCell(13).setCellValue(gstInvoice.getDateReceived());
            dataRow.createCell(14).setCellValue(gstInvoice.getInvoiceBalance().doubleValue());
            dataRow.createCell(15).setCellValue(gstInvoice.getStatus());
            dataRow.createCell(16).setCellValue(gstInvoice.getTdsCredited().doubleValue());
            dataRow.createCell(17).setCellValue(gstInvoice.getTdsBalance().doubleValue());
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

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @PutMapping("/updateGSTDetailsById/{id}")
    public HttpEntity<String> updateGSTDetailsById(@PathVariable("id") Long id, @RequestBody GSTInvoice updatedInvoice) {
        logger.info("Update request for ID '%s' requested " + id);
        GSTInvoice existingInvoice = gstInvoiceService.findById(id);
        if (existingInvoice != null) {
            existingInvoice.setInvoiceNumber(updatedInvoice.getInvoiceNumber());
            existingInvoice.setFy(updatedInvoice.getFy());
            existingInvoice.setInvoiceDate(updatedInvoice.getInvoiceDate());
            existingInvoice.setGstPeriod(updatedInvoice.getGstPeriod());
            existingInvoice.setBillingPeriod(updatedInvoice.getBillingPeriod());
            existingInvoice.setCustomerId(updatedInvoice.getCustomerId());
            existingInvoice.setPaidTo(updatedInvoice.getPaidTo());
            existingInvoice.setTaxableAmount(updatedInvoice.getTaxableAmount());
            existingInvoice.setTds(updatedInvoice.getTds());
            existingInvoice.setGst(updatedInvoice.getGst());
            existingInvoice.setInvoiceAmount(updatedInvoice.getInvoiceAmount());
            existingInvoice.setReceivable(updatedInvoice.getReceivable());
            existingInvoice.setAmountReceived(updatedInvoice.getAmountReceived());
            existingInvoice.setDateReceived(updatedInvoice.getDateReceived());
            existingInvoice.setInvoiceBalance(updatedInvoice.getInvoiceBalance());
            existingInvoice.setStatus(updatedInvoice.getStatus());
            existingInvoice.setTdsCredited(updatedInvoice.getTdsCredited());
            existingInvoice.setTdsBalance(updatedInvoice.getTdsBalance());

            GSTInvoice updated = gstInvoiceService.update(existingInvoice);
            return ResponseEntity.ok(GST_Constants.GST_DATA_UPDATED);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GST_Constants.INVOICE_NOT_FOUND + " " + id);
        }
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @DeleteMapping("/deleteGSTInvoiceById/{id}")
    public ResponseEntity<String> deleteGSTInvoiceById(@PathVariable("id") Long id) {
        logger.info("Delete GST details requested for ID {} ", id);
        boolean deleted = gstInvoiceService.delete(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(GST_Constants.GST_DATA_DELETED);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GST_Constants.INVOICE_NOT_FOUND + " " + id);
        }
    }

    private String generateInvoiceNumber() {
        int randomPart = new Random().nextInt(1000);
        String invoicePart = String.format("%03d", randomPart);
        String prefix = "AD" + String.format("%04d", new Random().nextInt(10000));
        return prefix + "-" + invoicePart;
    }

}
