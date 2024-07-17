package com.adt.expensemanagement.models;

import com.adt.expensemanagement.services.interfaces.CommonEmailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OnExpenseRequestSaveEventListener implements ApplicationListener<OnExpenseRequestSaveEvent> {

    private CommonEmailService emailService;

    @Autowired
    public OnExpenseRequestSaveEventListener(CommonEmailService emailService) {
        this.emailService = emailService;
    }


    @Override
    @Async
    public void onApplicationEvent(OnExpenseRequestSaveEvent event) {
        emailService.sendEmail(event);
    }



}


