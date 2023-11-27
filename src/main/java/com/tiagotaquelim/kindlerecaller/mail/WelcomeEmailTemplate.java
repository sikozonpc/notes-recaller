package com.tiagotaquelim.kindlerecaller.mail;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.tiagotaquelim.kindlerecaller.subscriber.Subscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WelcomeEmailTemplate extends BaseMailTemplate<Subscriber> {
    @Value("${sendgrid.welcome-template}")
    private String templateId;

    @Override
    public Mail compose(Subscriber data) {
        Email from = new Email(senderEmail);

        Mail mail = new Mail();

        mail.setTemplateId(templateId);
        mail.setFrom(from);
        mail.setSubject("Kindle Recaller - Welcome!");
        Personalization personalization = new Personalization();
        Email to = new Email(data.getEmail());

        personalization.addDynamicTemplateData("name", data.getName());
        personalization.addTo(to);

        return mail;
    }
}
