package com.adt.expensemanagement.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "expense_schema",name="expense_outbound")
public class ExpenseOutbound {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="expense_id")
	private int expenseId;
	@Column(name ="balance")
    private int balance;
	@Column(name ="billing_period")
    private String billingPeriod;
	@Column(name ="charged_amount")
    private int chargedAmount;
	@Column(name ="customer_id")
    private int customerId;
	@Column(name ="date")
    private LocalDate date;
	@Column(name ="date_recieved")
    private LocalDate dateRecieved;
	@Column(name ="gst")
    private int gst;
	@Column(name ="gst_period")
    private String gstPeriod;
	@Column(name ="inr_recievable")
    private int inrRecievable;
	@Column(name ="inr_recieved")
    private int inrRecieved;
	@Column(name ="invoice")
    private String invoice;
	@Column(name ="invoice_amt_inr")
    private int invoiceAmtInr;
	@Column(name ="mode")
    private String mode;
	@Column(name ="tds")
    private int tds;
    
}
