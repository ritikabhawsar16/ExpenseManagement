package com.adt.expensemanagement.models;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;


public class OnExpenseRequestSaveEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private ExpenseItems expenseItems;
    private UriComponentsBuilder approveUrlBuilder;
    private UriComponentsBuilder rejectUrlBuilder;


    public OnExpenseRequestSaveEvent(UriComponentsBuilder approveUrlBuilder, UriComponentsBuilder rejectUrlBuilder, ExpenseItems expenseItems) {
        super(expenseItems);
        this.expenseItems = expenseItems;
        this.approveUrlBuilder = approveUrlBuilder;
        this.rejectUrlBuilder = rejectUrlBuilder;

    }

    public UriComponentsBuilder getApproveUrlBuilder() {
        return approveUrlBuilder;
    }

    public UriComponentsBuilder getRejectUrlBuilder() {
        return rejectUrlBuilder;
    }

    public ExpenseItems getExpenseItems() {
        return expenseItems;
    }

}
