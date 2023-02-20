package com.expensemanagement.services.implementations;

import com.expensemanagement.models.ExpenseItems;
import com.expensemanagement.repositories.ExpenseRepository;
import com.expensemanagement.services.interfaces.ExpenseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final Logger LOGGER = LogManager.getLogger(ExpenseServiceImpl.class);

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public ExpenseItems createExpenses(ExpenseItems expenseItems) {

        return expenseRepository.save(expenseItems);
    }

    @Override
    public String updateExpense(int id, ExpenseItems expenseModel) {

        Optional<ExpenseItems> exModel = expenseRepository.findById(id);

        if (!exModel.isPresent()) {
            String message = messageSource.getMessage("api.error.data.not.found.id", null, Locale.ENGLISH);
            LOGGER.error(message=message+id);
            throw new EntityNotFoundException(message);
        }
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

    @Override
    public ExpenseItems getExpenseById(int id) throws NoSuchFieldException {
        Optional<ExpenseItems> expenseItems = expenseRepository.findById(id);
        if (!expenseItems.isPresent()) {
            String message = messageSource.getMessage("api.error.data.not.found.id", null, Locale.ENGLISH);
            LOGGER.error(message=message+id);
            throw new EntityNotFoundException(message);
        }
        return expenseItems.get();
    }
}
