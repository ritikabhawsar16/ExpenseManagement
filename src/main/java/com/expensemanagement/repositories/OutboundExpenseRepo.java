package com.expensemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensemanagement.models.ExpenseOutbound;

public interface OutboundExpenseRepo extends JpaRepository<ExpenseOutbound, Integer>{

}
