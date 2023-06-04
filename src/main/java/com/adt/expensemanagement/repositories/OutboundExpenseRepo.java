package com.adt.expensemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adt.expensemanagement.models.ExpenseOutbound;

public interface OutboundExpenseRepo extends JpaRepository<ExpenseOutbound, Integer>{

}
