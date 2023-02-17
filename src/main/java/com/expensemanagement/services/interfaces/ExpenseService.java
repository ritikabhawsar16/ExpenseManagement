package com.expensemanagement.services.interfaces;

import com.expensemanagement.models.ExpenseItems;
import com.expensemanagement.models.ExpenseOutbound;

public interface ExpenseService {
    public ExpenseItems createExpenses(ExpenseItems expenseItems);

    public String updateExpense(int id,ExpenseItems expenseItems);

    public ExpenseItems getExpenseById(int id) throws NoSuchFieldException;

	public String saveOutboundExpense(ExpenseOutbound expenseOutbound);

	public String updateOutboundExpense(ExpenseOutbound expenseOutbound);
}
