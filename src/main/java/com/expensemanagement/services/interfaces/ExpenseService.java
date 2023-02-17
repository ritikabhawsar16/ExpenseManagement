package com.expensemanagement.services.interfaces;

import org.springframework.stereotype.Repository;

import com.expensemanagement.models.ExpenseItems;

@Repository
public interface ExpenseService {
    public ExpenseItems createExpenses(ExpenseItems expenseItems);

    public String updateExpense(int id,ExpenseItems expenseItems);

<<<<<<< HEAD
=======
    public ExpenseItems getExpenseById(int id) throws NoSuchFieldException;
>>>>>>> 685a2f0ba8dd44cd71939e073bea6a4b536a3620
}
