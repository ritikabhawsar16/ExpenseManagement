package com.adt.expensemanagement.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
