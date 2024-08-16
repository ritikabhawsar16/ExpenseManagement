package com.adt.expensemanagement.models;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;


public class OnExpenseRequestSaveEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private ExpenseItems expenseItem;
    private String action;
    private String actionStatus;
    private UriComponentsBuilder approveUrlBuilder;
    private UriComponentsBuilder rejectUrlBuilder;


    public OnExpenseRequestSaveEvent(ExpenseItems expenseItem, String action, String actionStatus) {
        super(expenseItem);
        this.expenseItem = expenseItem;
        this.action = action;
        this.actionStatus = actionStatus;
    }

    public OnExpenseRequestSaveEvent(UriComponentsBuilder approveUrlBuilder, UriComponentsBuilder rejectUrlBuilder, ExpenseItems expenseItem) {
        super(expenseItem);
        this.expenseItem = expenseItem;
        this.approveUrlBuilder = approveUrlBuilder;
        this.rejectUrlBuilder = rejectUrlBuilder;
    }

    public ExpenseItems getExpenseItem() {
        return expenseItem;
    }

    public void setExpenseItem(ExpenseItems expenseItem) {
        this.expenseItem = expenseItem;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public UriComponentsBuilder getApproveUrlBuilder() {
        return approveUrlBuilder;
    }

    public void setApproveUrlBuilder(UriComponentsBuilder approveUrlBuilder) {
        this.approveUrlBuilder = approveUrlBuilder;
    }

    public UriComponentsBuilder getRejectUrlBuilder() {
        return rejectUrlBuilder;
    }

    public void setRejectUrlBuilder(UriComponentsBuilder rejectUrlBuilder) {
        this.rejectUrlBuilder = rejectUrlBuilder;
    }
}