package com.adt.expensemanagement.services.interfaces;

import com.adt.expensemanagement.models.GSTInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GSTInvoiceService {

    void save(GSTInvoice gstInvoice);

    GSTInvoice findById(Long id);

    List<GSTInvoice> findAll();

    Page<GSTInvoice> findAll(Pageable pageable);

    public GSTInvoice update(GSTInvoice invoice);

    public boolean delete(Long id);

    List<GSTInvoice> findByInvoiceNumberOrCustomerId(String invoiceNumber, String customerId);
}

