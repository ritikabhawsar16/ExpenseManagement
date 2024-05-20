package com.adt.expensemanagement.repositories;

import com.adt.expensemanagement.models.GSTInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GSTInvoiceRepository extends JpaRepository<GSTInvoice, Long> {

    @Query("SELECT i FROM GSTInvoice i WHERE i.invoiceNumber = :invoiceNumber OR i.customerId = :customerId")
    List<GSTInvoice> findInvoiceNumberOrCustomerId(String invoiceNumber, String customerId);

    Page<GSTInvoice> findAll(Pageable pageable);

    @Query("SELECT i FROM GSTInvoice i WHERE i.invoiceNumber = :invoiceNumber")
    GSTInvoice findByInvoiceNumber(String invoiceNumber);

    @Modifying
    @Transactional
    @Query("DELETE FROM GSTInvoice i WHERE i.invoiceNumber = :invoiceNumber")
    int deleteByInvoiceNumber(String invoiceNumber);
}
