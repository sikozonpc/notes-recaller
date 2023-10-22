package com.tiagotaquelim.kindlerecaller.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.tiagotaquelim.kindlerecaller.pojos.EmailHighlightField;
import com.tiagotaquelim.kindlerecaller.subscriber.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SendGridService {
    private static final Logger logger = LoggerFactory.getLogger(SendGridService.class);

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.sender}")
    private String senderEmail;

    @Value("${sendgrid.template}")
    private String templateId;

    public void sendTextEmail(List<Subscriber> subscribers, List<EmailHighlightField> emailFields) throws IOException {
        Mail mail = getMailTemplate(subscribers, emailFields);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }

    private Mail getMailTemplate(List<Subscriber> subscribers, List<EmailHighlightField> emailFields) {
        Email from = new Email(senderEmail);

        Mail mail = new Mail();

        mail.setTemplateId(templateId);
        mail.setFrom(from);
        mail.setSubject("Kindle Recaller - Daily Highlights");
        Personalization personalization = new Personalization();

        subscribers.forEach(subscriber -> {
            Email to = new Email(subscriber.getEmail());
            personalization.addTo(to);
        });

        for (int i = 0; i < emailFields.size(); i++) {
            Number index = i + 1;
            personalization.addDynamicTemplateData("quote" + index, emailFields.get(i).text());
            personalization.addDynamicTemplateData("footer" + index, emailFields.get(i).footer());

            String notes = emailFields.get(i).notes() != null
                    ? "Notes: " + emailFields.get(i).notes()
                    : "";

            personalization.addDynamicTemplateData("notes" + index, notes);

            logger.info("Quote returned: " + emailFields.get(i).text());
        }

        mail.addPersonalization(personalization);
        return mail;
    }
}