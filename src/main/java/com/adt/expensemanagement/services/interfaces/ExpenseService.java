package com.adt.expensemanagement.services.interfaces;


import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.adt.expensemanagement.models.ExpenseItems;
import com.adt.expensemanagement.models.ExpenseOutbound;

@Repository
public interface ExpenseService {
    public ExpenseItems createExpenses(ExpenseItems expenseItems);

    public String updateExpense(int id,ExpenseItems expenseItems);

    public ExpenseItems getExpenseById(int id) throws NoSuchFieldException;

	public String saveOutboundExpense(ExpenseOutbound expenseOutbound);

	public String updateOutboundExpense(ExpenseOutbound expenseOutbound);

    public List<ExpenseItems> getAllExpenses();

    public List<ExpenseItems> getExpenseByDateRange(String from, String to) throws ParseException;

	public String deleteAllExpenseByIds(List<Integer> ids);
    public String deleteExpenseById(int id);

}
