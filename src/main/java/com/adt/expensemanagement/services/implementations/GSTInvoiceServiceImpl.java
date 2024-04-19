package com.adt.expensemanagement.services.implementations;

import com.adt.expensemanagement.models.GSTInvoice;
import com.adt.expensemanagement.repositories.GSTInvoiceRepository;
import com.adt.expensemanagement.services.interfaces.GSTInvoiceService;
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
