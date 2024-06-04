package com.adt.expensemanagement.services.implementations;

import com.adt.expensemanagement.models.GSTInvoice;
import com.adt.expensemanagement.repositories.GSTInvoiceRepository;
import com.adt.expensemanagement.services.interfaces.GSTInvoiceService;
import com.adt.expensemanagement.util.GSTInvoiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
      if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getTaxableAmount())){
    throw new IllegalArgumentException("Invalid Tax Amount");
}
        if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getTds())){
    throw new IllegalArgumentException("Invalid TDS amount");
        }
        if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getGst())){
    throw new IllegalArgumentException("Invalid GST Amount");
}
        if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getInvoiceAmount())){
            throw new IllegalArgumentException("Invalid Invoice Amount");
        }
        if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getReceivable())){
            throw new IllegalArgumentException("Invalid Recievable Amount");
        }
        if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getAmountReceived())){
            throw new IllegalArgumentException("Invalid Amount");
        }
        if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getInvoiceBalance())){
            throw new IllegalArgumentException("Invalid Invoice");
        }
        if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getTdsCredited())){
            throw new IllegalArgumentException("Invalid TDS Credit Details");
        }
        if(GSTInvoiceUtility.validateBigdecimal(gstInvoice.getTdsBalance())){
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
