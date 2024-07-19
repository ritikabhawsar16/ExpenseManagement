package com.adt.expensemanagement.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tds_details", catalog= "EmployeeDB", schema = "expense_schema")
public class TDSDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal tds;
    private BigDecimal tdsbalance;
    private BigDecimal tdscredited;
    @OneToOne
    @JoinColumn(name = "invoice_number", referencedColumnName = "invoiceNumber", nullable = false,insertable=false, updatable=false)
    private GSTInvoice gstInvoice;
    private String invoice_number;

}
