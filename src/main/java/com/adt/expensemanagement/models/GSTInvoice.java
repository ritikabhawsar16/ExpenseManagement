package com.adt.expensemanagement.models;
import com.adt.expensemanagement.util.InvoiceNumberGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
	@Column(unique = true, nullable = false)
	private String invoiceNumber;
	private String fy;
	private Date invoiceDate;
	private String gstPeriod;
	private String billingPeriod;
	private String customerId;
	private String paidTo;
	private BigDecimal taxableAmount;
//	private BigDecimal tds;
	private BigDecimal gst;
	private BigDecimal invoiceAmount;
	private BigDecimal receivable;
	private BigDecimal amountReceived;
	private Date dateReceived;
	private BigDecimal invoiceBalance;
	private String status;
	//private BigDecimal tdsCredited;
	//private BigDecimal tdsBalance;
	private boolean projectType;

	@Transient
	private TDSDetails tdsDetails;

}