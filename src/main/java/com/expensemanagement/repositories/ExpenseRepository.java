package com.expensemanagement.repositories;

import com.expensemanagement.models.ExpenseItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseItems, Integer> {
}
