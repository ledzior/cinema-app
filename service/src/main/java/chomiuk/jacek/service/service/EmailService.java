package chomiuk.jacek.service.service;

import chomiuk.jacek.service.dto.EmailDataDto;
import chomiuk.jacek.service.exception.EmailException;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class EmailService {
    private static String EMAIL_ADDRESS = "budka.z.napojami@gmail.com";
    private static String EMAIL_PASSWORD = "rootroot";

    public static void send(EmailDataDto emailDataDto) {
        try {
            System.out.println("Sending email .....");
            Session session = createSession();
            MimeMessage message = new MimeMessage(session);
            prepareEmailMessage(message, emailDataDto);
            Transport.send(message);
            System.out.println("Email has been sent");
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailException(e.getMessage());
        }
    }

    // send email message with an attachment
    // https://j2html.com/
    private static void prepareEmailMessage(MimeMessage mimeMessage, EmailDataDto emailDataDto) {
        try {
            System.out.println(emailDataDto);
            mimeMessage.setContent(emailDataDto.getHtml(), "text/html;charset=utf-8");
            mimeMessage.setFrom(new InternetAddress(EMAIL_ADDRESS));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDataDto.getTo()));
            mimeMessage.setSubject(emailDataDto.getTitle());
        } catch (Exception e) {
            throw new EmailException(e.getMessage());
        }
    }

    private static Session createSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_ADDRESS, EMAIL_PASSWORD);
            }
        });
    }
}
