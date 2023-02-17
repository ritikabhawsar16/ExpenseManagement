package com.expensemanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(catalog = "EmployeeDB", schema = "expense_schema",name="ExpenseManagement")
public class ExpenseItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    @Column(name="amount", nullable = false)
    private int amount;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="payment_mode", nullable = false)
    private String paymentMode;

    @Column(name="payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name="created_by", nullable = false)
    private String createdBy;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="GST", nullable = false)
    private boolean gst;

    @Column(name="paid_by", nullable = false)
    private String paidBy;

    @Column(name="comments", nullable = false)
    private String comments;
}
