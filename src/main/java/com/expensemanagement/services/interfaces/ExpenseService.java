package com.expensemanagement.services.interfaces;

import com.expensemanagement.models.ExpenseItems;

public interface ExpenseService {
    public ExpenseItems createExpenses(ExpenseItems expenseItems);

    public String updateExpense(int id,ExpenseItems expenseItems);

    public ExpenseItems getExpenseById(int id) throws NoSuchFieldException;
}
