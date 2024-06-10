package com.adt.expensemanagement.services.implementations;

import com.adt.expensemanagement.models.GSTInvoice;
import com.adt.expensemanagement.repositories.GSTInvoiceRepository;
import com.adt.expensemanagement.services.interfaces.GSTInvoiceService;
import com.adt.expensemanagement.util.GSTInvoiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class GSTInvoiceServiceImpl implements GSTInvoiceService {

    @Autowired
    private GSTInvoiceRepository invoiceRepository;

    @Override
    public void save(GSTInvoice gstInvoice) {
        if(!GSTInvoiceUtility.checkValidate(gstInvoice.getInvoiceNumber())){
            throw new IllegalArgumentException("Invalid GST Number");
        }
        if(!GSTInvoiceUtility.validatePeriod(gstInvoice.getFy())){
            throw new IllegalArgumentException("Invalid Details");
        }
        if(!GSTInvoiceUtility.checkValidate(gstInvoice.getGstPeriod())){
            throw new IllegalArgumentException("Invalid GST Period");
        }
        if(!GSTInvoiceUtility.checkValidate(gstInvoice.getBillingPeriod())){
            throw new IllegalArgumentException("Invalid Billing Period");
        }
        if(!GSTInvoiceUtility.checkValidate(gstInvoice.getCustomerId())){
            throw new IllegalArgumentException("Invalid Customer Id");
        }
        if(!GSTInvoiceUtility.validateString(gstInvoice.getPaidTo())){
            throw new IllegalArgumentException("Invalid Paid details");
        }
        if(!GSTInvoiceUtility.validateString(gstInvoice.getStatus())){
            throw new IllegalArgumentException("Invalid Status format");
        }
        BigDecimal taxableAmount = new BigDecimal(String.valueOf(gstInvoice.getTaxableAmount())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(taxableAmount)){
           throw new IllegalArgumentException("Invalid Tax Amount");
        }
        BigDecimal tds = new BigDecimal(String.valueOf(gstInvoice.getTds())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(tds)){
            throw new IllegalArgumentException("Invalid TDS amount");
        }
        BigDecimal gst = new BigDecimal(String.valueOf(gstInvoice.getGst())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(gst)){
            throw new IllegalArgumentException("Invalid GST Amount");
        }
        BigDecimal invoiceAmount = new BigDecimal(String.valueOf(gstInvoice.getInvoiceAmount())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(invoiceAmount)){
            throw new IllegalArgumentException("Invalid Invoice Amount");
        }
        BigDecimal receivable = new BigDecimal(String.valueOf(gstInvoice.getReceivable())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(receivable)){
            throw new IllegalArgumentException("Invalid Receivable Amount");
        }
        BigDecimal amountReceived = new BigDecimal(String.valueOf(gstInvoice.getAmountReceived())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(amountReceived)){
            throw new IllegalArgumentException("Invalid Amount");
        }
        BigDecimal invoiceBalance = new BigDecimal(String.valueOf(gstInvoice.getInvoiceBalance())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(invoiceBalance)){
            throw new IllegalArgumentException("Invalid Invoice");
        }
        BigDecimal tdsCredited = new BigDecimal(String.valueOf(gstInvoice.getTdsCredited())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(tdsCredited)){
            throw new IllegalArgumentException("Invalid TDS Credit Details");
        }
        BigDecimal tdsBalance = new BigDecimal(String.valueOf(gstInvoice.getTdsBalance())).setScale(2);
        if(GSTInvoiceUtility.validateBigDecimal(tdsBalance)){
            throw new IllegalArgumentException("Invalid TDS Balance");
        }

        invoiceRepository.save(gstInvoice);
    }

    @Override
    public GSTInvoice findById(Long id) {
        GSTInvoice invoice = invoiceRepository.findById((id)).orElse(null);
        return invoice;
    }

    @Override
    public List<GSTInvoice> findAll() {
        List<GSTInvoice> gstInvoiceList = invoiceRepository.findAll();
        return gstInvoiceList;
    }

    public Page<GSTInvoice> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public GSTInvoice update(GSTInvoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public GSTInvoice findByInvoiceNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }

    @Override
    public int deleteByInvoiceNumber(String invoiceNumber) {
        int result = invoiceRepository.deleteByInvoiceNumber(invoiceNumber);
        if(result !=0)
            return 1;
        else
            return 0;
    }

}
