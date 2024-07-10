package com.adt.expensemanagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GSTInvoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String invoiceNumber;
	private String fy;
	private Date invoiceDate;
	private String gstPeriod;
	private String billingPeriod;
	private String customerId;

	private String paidTo;
	private BigDecimal taxableAmount;
	private BigDecimal tds;
	private BigDecimal gst;
	private BigDecimal invoiceAmount;
	private BigDecimal receivable;
	private BigDecimal amountReceived;
	private Date dateReceived;
	private BigDecimal invoiceBalance;
	private String status;
	private BigDecimal tdsCredited;
	private BigDecimal tdsBalance;

}