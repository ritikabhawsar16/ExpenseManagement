package com.adt.expensemanagement.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(catalog = "EmployeeDB", schema = "expense_schema",name="ExpenseManagement")

public class ExpenseItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    @NotBlank
    @Column(name="amount", nullable = false)
    private int amount;

    @Column(name = "employee_id",nullable = false)
    private Integer empId;

    @Column(name = "status")
    private String status;


    @Column(name="description", nullable = false)
    private String description;

//    @Column(name="created_by")
//    private String createdBy;

    @Column(name="payment_mode", nullable = false)
    private String paymentMode;


    @Column(name="payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="GST", nullable = false)
    private boolean gst;

    @Column(name="paid_by")
    private String paidBy;

    @Column(name="settled_date")
    private LocalDate settledDate;

    @Column(name="comments", nullable = false)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "EMPLOYEE_ID" ,nullable = false, insertable = false, updatable = false)
    private User employee;
}
