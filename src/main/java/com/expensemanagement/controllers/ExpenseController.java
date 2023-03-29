package com.expensemanagement.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.expensemanagement.models.ExpenseItems;
import com.expensemanagement.models.ExpenseOutbound;
import com.expensemanagement.services.interfaces.ExpenseService;

import java.text.ParseException;
import java.util.List;

@RestController
public class ExpenseController {

    private static final Logger LOGGER = LogManager.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/getAllExpenses")
    public ResponseEntity<List<ExpenseItems>> getAllExpenses(HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        List<ExpenseItems> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

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
    
    @PostMapping("/saveOutboundExpense")
    public ResponseEntity<String> saveOutboundExpense(@RequestBody ExpenseOutbound expenseOutbound,HttpServletRequest request){
    	
    	  LOGGER.info("API Call From IP: " + request.getRemoteHost());
    	return new ResponseEntity<>(expenseService.saveOutboundExpense(expenseOutbound),HttpStatus.OK);
    }
    
    @PutMapping("updateOutboundExpense")
    public ResponseEntity<String> updateOutboundExpense(@RequestBody ExpenseOutbound expenseOutbound,HttpServletRequest request){
  	  LOGGER.info("API Call From IP: " + request.getRemoteHost());
  	return new ResponseEntity<>(expenseService.updateOutboundExpense(expenseOutbound),HttpStatus.OK);
  }

    @GetMapping("/getExpenseByDateRange")
    public ResponseEntity<List<ExpenseItems>> getExpenseByDateRange(@RequestParam("startDate") String from, @RequestParam("endDate") String to, HttpServletRequest request) throws NoSuchFieldException, ParseException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<>(expenseService.getExpenseByDateRange(from, to), HttpStatus.OK);
    }
    

}
