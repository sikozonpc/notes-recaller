package com.tiagotaquelim.kindlerecaller.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.tiagotaquelim.kindlerecaller.subscriber.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SendGridService {
    private static final Logger logger = LoggerFactory.getLogger(SendGridService.class);

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.senderEmail}")
    private String senderEmail;

    @Value("${sendgrid.templateId}")
    private String templateId;

    public void sendTextEmail(List<Subscriber> subscribers, Map<String, String> quotesWithAuthors) throws IOException {
        Mail mail = getMailTemplate(subscribers, quotesWithAuthors);

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

    private Mail getMailTemplate(List<Subscriber> subscribers, Map<String, String> quotesWithAuthors) {
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

        for (int i = 0; i < quotesWithAuthors.size(); i++) {
            String quote = quotesWithAuthors.entrySet().stream().skip(i).findFirst().get().getKey();
            String footer = quotesWithAuthors.entrySet().stream().skip(i).findFirst().get().getValue();

            String quoteName = "quote" + (i + 1);
            personalization.addDynamicTemplateData(quoteName, quote);

            String footerName = "quote" + (i + 1) + "footer";
            personalization.addDynamicTemplateData(footerName, footer);
        }

        mail.addPersonalization(personalization);
        return mail;
    }
}