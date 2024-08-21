package com.adt.expensemanagement.services.implementations;

import com.adt.expensemanagement.config.Auth;
import com.adt.expensemanagement.models.*;
import com.adt.expensemanagement.repositories.UserRepo;
import com.adt.expensemanagement.services.interfaces.CommonEmailService;
import com.adt.expensemanagement.util.TableDataExtractor;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailService implements CommonEmailService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Configuration templateConfiguration;

    @Value("${app.velocity.templates.location}")
    private String basePackagePath;

    @Autowired
    private TableDataExtractor dataExtractor;

    @Autowired
    private Auth auth;

    @Value("${email.service.url}")
    private String emailServiceUrl;

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void sendEmail(OnExpenseRequestSaveEvent event, String approveUrl, String rejectUrl, ExpenseItems expenseItems) throws TemplateException, IOException {
        Mail mail = new Mail();
        mail.setSubject("Expense Approval Request");

        //*** From whom the mail should come ***
        Integer empID = expenseItems.getEmpId();
        Optional<User> user = userRepo.findById(empID);
        String userEmail = user.get().getEmail();
        String employeeName = user.get().getFirstName() + " " + user.get().getLastName();
        mail.setFrom(userEmail);

        //*** Get recipient email and generate token ***
        String sql = "SELECT email_id FROM av_schema.priortime_email where designation='CEO'";
        List<Map<String, Object>> ExpenseData = dataExtractor.extractDataFromTable(sql);
        for (Map<String, Object> expenses : ExpenseData) {
            String email = String.valueOf(expenses.get("email_id"));
            String token = auth.tokenGanreate(email);
            mail.setTo(email);
            mail.getModel().put("approvalUrl", approveUrl + "?Authorization=" + token);
            mail.getModel().put("rejectionUrl", rejectUrl + "?Authorization=" + token);
            mail.getModel().put("EmployeeName", employeeName);
            mail.getModel().put("ExpenseAmount", String.valueOf(event.getExpenseItem().getAmount()));
            mail.getModel().put("ExpenseDate", String.valueOf(event.getExpenseItem().getPaymentDate()));
            mail.getModel().put("ExpensePurpose", event.getExpenseItem().getDescription().toString());

            templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
            Template template = templateConfiguration.getTemplate("expense_status_approval.ftl");
            String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());
            mail.setContent(mailContent);
            String url = emailServiceUrl + "/utility/emails/send";
            HttpEntity<Mail> request = new HttpEntity<>(mail);
            restTemplate.postForEntity(url, request, String.class);

        }
    }

    @Override
    public void sendExpenseApproveAndRejectedEmail(OnExpenseRequestSaveEvent event) {
        ExpenseItems expense = event.getExpenseItem();
        Integer empID = expense.getEmpId();
        Optional<User> user = userRepo.findById(empID);
        String userEmail = user.get().getEmail();
        String employeeName = user.get().getFirstName() + " " + user.get().getLastName();
        String subject = "Expense " + event.getActionStatus();
        String message = "Your expense request has been " + event.getActionStatus()+".Below are the expense request details.";

        Mail mail = new Mail();
        mail.setSubject(subject);
       // mail.setFrom(sender);
        mail.setTo(userEmail);

        Map<String, Object> model = new HashMap<>();
        model.put("Message", message);
        model.put("Name", employeeName);
        model.put("ExpenseAmount", String.valueOf(event.getExpenseItem().getAmount()));
        model.put("ExpenseDate", String.valueOf(event.getExpenseItem().getPaymentDate()));
        model.put("ExpensePurpose", event.getExpenseItem().getDescription().toString());

        try {
            templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
            Template template = templateConfiguration.getTemplate("approve_and_reject_expense_request.ftl");
            String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            mail.setContent(mailContent);
            String url = emailServiceUrl + "/utility/emails/send";
            HttpEntity<Mail> request = new HttpEntity<>(mail);
            restTemplate.postForEntity(url, request, String.class);
        } catch (IOException | TemplateException e) {
            log.error("Error while sending expense status email: ", e);
        }
    }

    @Override
    public void sendEmail(OnExpenseRequestSaveEvent event) {
        ExpenseItems expenseItems = event.getExpenseItem();
        String emailApprovalUrl = event.getApproveUrlBuilder().toUriString();
        String emailRejectionUrl = event.getRejectUrlBuilder().toUriString();

        try {
            sendEmail(event, emailApprovalUrl, emailRejectionUrl, expenseItems);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
