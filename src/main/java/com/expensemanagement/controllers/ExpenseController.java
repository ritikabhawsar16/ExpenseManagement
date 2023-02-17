package com.expensemanagement.controllers;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expensemanagement.models.ExpenseItems;
import com.expensemanagement.services.interfaces.ExpenseService;

@RestController
public class ExpenseController {

    private static final Logger LOGGER = LogManager.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;
    

    @PostMapping("/createExpenses")
    public ResponseEntity<ExpenseItems> createExpenses(@RequestBody @Valid ExpenseItems expenseItems, HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        ExpenseItems expenses=expenseService.createExpenses(expenseItems);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PutMapping("/updateExpense/{id}")
    public ResponseEntity<String> updateExpense(@PathVariable("id") int id,@RequestBody ExpenseItems expenseItems, HttpServletRequest request){
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return  new ResponseEntity<>(this.expenseService.updateExpense(id,expenseItems), HttpStatus.OK);
    }

    @GetMapping("/getExpenseById/{id}")
    public ResponseEntity<ExpenseItems> getExpenseById(@PathVariable("id") int id, HttpServletRequest request) throws NoSuchFieldException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<>(expenseService.getExpenseById(id), HttpStatus.OK);
    }

}
