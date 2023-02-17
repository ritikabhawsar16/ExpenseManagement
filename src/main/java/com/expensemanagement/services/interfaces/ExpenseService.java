package com.expensemanagement.services.interfaces;

import org.springframework.stereotype.Repository;

import com.expensemanagement.models.ExpenseItems;

@Repository
public interface ExpenseService {
    public ExpenseItems createExpenses(ExpenseItems expenseItems);

    public String updateExpense(int id,ExpenseItems expenseItems);


    public ExpenseItems getExpenseById(int id) throws NoSuchFieldException;
}
