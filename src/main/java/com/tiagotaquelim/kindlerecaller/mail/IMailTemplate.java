package com.tiagotaquelim.kindlerecaller.mail;

import com.sendgrid.helpers.mail.Mail;

import java.io.IOException;

public interface IMailTemplate<T> {
    Mail compose(T data);

    void send(Mail mail) throws IOException;
}
