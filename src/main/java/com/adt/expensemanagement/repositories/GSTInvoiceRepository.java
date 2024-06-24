package com.adt.expensemanagement.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adt.expensemanagement.models.GSTInvoice;

@Repository
public interface GSTInvoiceRepository extends JpaRepository<GSTInvoice, Long> {

	@Query(value = "SELECT * FROM expense_schema.gstinvoice i WHERE i.invoice_number = :invoiceNumber OR i.customer_id = :customerId", nativeQuery = true)
	List<GSTInvoice> findInvoiceNumberOrCustomerId(String invoiceNumber, String customerId);

	Page<GSTInvoice> findAll(Pageable pageable);

	@Query(value = "SELECT * FROM expense_schema.gstinvoice WHERE invoice_number = :invoiceNumber", nativeQuery = true)
	GSTInvoice findByInvoiceNumber(String invoiceNumber);

	@Query(value = "DELETE FROM expense_schema.gstinvoice i WHERE i.invoice_number = :invoiceNumber", nativeQuery = true)
	int deleteByInvoiceNumber(String invoiceNumber);

}
