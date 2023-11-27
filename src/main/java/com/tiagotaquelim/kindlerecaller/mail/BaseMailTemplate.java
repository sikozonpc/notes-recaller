package com.tiagotaquelim.kindlerecaller.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public abstract class BaseMailTemplate<T> implements IMailTemplate<T> {

    public static final Logger logger = LoggerFactory.getLogger(BaseMailTemplate.class);

    @Value("${sendgrid.api.key}")
    public String sendGridApiKey;

    @Value("${sendgrid.sender}")
    public String senderEmail;

    public void send(Mail mail) throws IOException {
        if (mail.from.getEmail() == null) {
            throw new RuntimeException("Sender email not set");
        }

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        response.getBody();
        logger.info("Email sent");
    }

    public void composeAndSend(T data) throws IOException {
        this.send(this.compose(data));
    }

    public void composeAndSend(List<T> data) {
        data.stream()
                .map(this::compose)
                .forEach(mail -> {
                    try {
                        this.send(mail);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
