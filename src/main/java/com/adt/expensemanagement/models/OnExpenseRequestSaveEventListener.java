package com.adt.expensemanagement.models;

import com.adt.expensemanagement.services.interfaces.CommonEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;



@Component
public class OnExpenseRequestSaveEventListener implements ApplicationListener<OnExpenseRequestSaveEvent> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private CommonEmailService emailService;

    @Autowired
    public OnExpenseRequestSaveEventListener(CommonEmailService emailService) {
        this.emailService = emailService;
    }


    @Override
    @Async
    public void onApplicationEvent(OnExpenseRequestSaveEvent event) {

        if (event.getAction() != null && event.getActionStatus() != null) {
            LOGGER.info("Handling expense approval/rejection event");
            emailService.sendExpenseApproveAndRejectedEmail(event);
        }

        else if (event.getApproveUrlBuilder() != null && event.getRejectUrlBuilder() != null) {
            LOGGER.info("Handling expense request save event");
            emailService.sendEmail(event);
        } else {
            LOGGER.warn("Unhandled event type");
        }
    }
}







