package com.expensemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensemanagement.models.ExpenseItems;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseItems, Integer> {
}
