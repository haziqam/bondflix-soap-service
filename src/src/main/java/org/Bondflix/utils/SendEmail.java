package org.Bondflix.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    private final Properties prop;

    public SendEmail() {
        prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        prop.put("mail.smtp.port", "2525");
    }

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        Dotenv dotenv = Dotenv.load();
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(dotenv.get("MAILTRAP_USERNAME"), dotenv.get("MAILTRAP_PASSWORD"));
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("13521135@std.stei.itb.ac.id"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);
    }
}
