package com.expensemanagement.services.implementations;

import com.expensemanagement.models.ExpenseItems;
import com.expensemanagement.repositories.ExpenseRepository;
import com.expensemanagement.services.interfaces.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Override
    public ExpenseItems createExpenses(ExpenseItems expenseItems) {
        ExpenseItems expenseResponse = expenseRepository.save(expenseItems);
        return expenseResponse;
    }

    @Override
    public String updateExpense(int id,ExpenseItems expenseModel) {

       Optional<ExpenseItems> exModel = expenseRepository.findById(id);

       if(exModel.isPresent()) {
           exModel.get().setAmount(expenseModel.getAmount());
           exModel.get().setCreatedBy(expenseModel.getCreatedBy());
           exModel.get().setDescription(expenseModel.getDescription());
           exModel.get().setPaymentDate(expenseModel.getPaymentDate());
           exModel.get().setPaymentMode(expenseModel.getPaymentMode());
           exModel.get().setGst(expenseModel.isGst());
           exModel.get().setPaidBy(expenseModel.getPaidBy());
           exModel.get().setComments(expenseModel.getComments());
           expenseRepository.save(exModel.get());
           return "update successfully";
       }
        return "update fail";
    }
}
