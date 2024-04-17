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

    public boolean delete(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<GSTInvoice> findByInvoiceNumberOrCustomerId(String invoiceNumber, String customerId) {
        return invoiceRepository.findInvoiceNumberOrCustomerId(invoiceNumber, customerId);
    }

}
