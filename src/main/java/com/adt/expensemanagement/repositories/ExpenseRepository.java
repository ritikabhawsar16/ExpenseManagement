package com.adt.expensemanagement.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adt.expensemanagement.models.ExpenseItems;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseItems, Integer> {
    @Query(value = "SELECT * FROM expense_schema.expense_management WHERE payment_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<ExpenseItems> getExpenseByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
