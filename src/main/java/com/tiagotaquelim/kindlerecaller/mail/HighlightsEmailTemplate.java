package com.tiagotaquelim.kindlerecaller.mail;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.tiagotaquelim.kindlerecaller.pojos.SubscriberEmailData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HighlightsEmailTemplate extends BaseMailTemplate<SubscriberEmailData> {
    @Value("${sendgrid.highlights-template}")
    private String templateId;

    @Override
    public Mail compose(SubscriberEmailData data) {
        Email from = new Email(this.senderEmail);

        Mail mail = new Mail();

        mail.setTemplateId(templateId);
        mail.setFrom(from);
        mail.setSubject("Kindle Recaller - Daily Highlights");
        Personalization personalization = new Personalization();

        Email to = new Email(data.email());
        personalization.addTo(to);

        for (int i = 0; i < data.highlights().size(); i++) {
            Number index = i + 1;
            personalization.addDynamicTemplateData("quote" + index, data.highlights().get(i).text());
            personalization.addDynamicTemplateData("footer" + index, data.highlights().get(i).footer());

            String notes = data.highlights().get(i).notes() != null
                    ? "Notes: " + data.highlights().get(i).notes()
                    : "";

            personalization.addDynamicTemplateData("notes" + index, notes);

            logger.info("Quote returned: " + data.highlights().get(i).text());
        }

        mail.addPersonalization(personalization);
        return mail;
    }
}
