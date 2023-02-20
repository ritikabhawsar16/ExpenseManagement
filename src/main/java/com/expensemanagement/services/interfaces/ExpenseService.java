package com.expensemanagement.services.interfaces;

import org.springframework.stereotype.Repository;

import com.expensemanagement.models.ExpenseItems;
import com.expensemanagement.models.ExpenseOutbound;

@Repository
public interface ExpenseService {
    public ExpenseItems createExpenses(ExpenseItems expenseItems);

    public String updateExpense(int id,ExpenseItems expenseItems);


    public ExpenseItems getExpenseById(int id) throws NoSuchFieldException;

	public String saveOutboundExpense(ExpenseOutbound expenseOutbound);

	public String updateOutboundExpense(ExpenseOutbound expenseOutbound);
}
