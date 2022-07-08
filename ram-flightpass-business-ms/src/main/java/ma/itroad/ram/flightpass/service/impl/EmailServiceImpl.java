package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Override
    @Async
    public void send(String to, String email, MultipartFile file) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper;

            helper = new MimeMessageHelper(mimeMessage, true);
            // Set Subject: header field
//            mimeMessage.setSubject("flightpass - achat "+ UUID.randomUUID().toString());
//            mimeMessage.setFrom(new InternetAddress("ram@gmail.com"));
//            mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            helper.setSubject("Injection d'une facture - Portail facturation fournisseurs de Royal Air Maroc");
            helper.setFrom(new InternetAddress("ram@gmail.com"));
            helper.setTo(to);


            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setContent(email, "text/html");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            if (file != null) {
                MimeBodyPart attachPart = new MimeBodyPart();

                attachPart.setContent(file.getBytes(), file.getContentType());
                attachPart.setFileName(file.getOriginalFilename());
                attachPart.setDisposition(Part.ATTACHMENT);
                multipart.addBodyPart(attachPart);
            }


            // Send the complete message parts
            mimeMessage.setContent(multipart);
            mailSender.send(mimeMessage);


        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel, String template,MultipartFile file) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);

        send(to, htmlBody, file);
    }


}
