package com.expensemanagement.controllers;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expensemanagement.models.ExpenseItems;
import com.expensemanagement.services.interfaces.ExpenseService;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    

    @PostMapping("/createExpenses")
    public ResponseEntity<ExpenseItems> createExpenses(@RequestBody @Valid ExpenseItems expenseItems) {
        ExpenseItems expenses=expenseService.createExpenses(expenseItems);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PutMapping("/updateExpense/{id}")
    public ResponseEntity<String> updateExpense(@PathVariable("id") int id,@RequestBody ExpenseItems expenseItems){

        return  new ResponseEntity<>(this.expenseService.updateExpense(id,expenseItems), HttpStatus.OK);
    }


}
