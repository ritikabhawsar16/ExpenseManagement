package com.adt.expensemanagement.services.interfaces;

import com.adt.expensemanagement.models.ExpenseItems;
import com.adt.expensemanagement.models.OnExpenseRequestSaveEvent;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

public interface CommonEmailService {

    void sendEmail(OnExpenseRequestSaveEvent event, String approveUrl, String rejectUrl, ExpenseItems expenseItems) throws TemplateException, IOException, MessagingException ;

    public void sendEmail(OnExpenseRequestSaveEvent event);
}
