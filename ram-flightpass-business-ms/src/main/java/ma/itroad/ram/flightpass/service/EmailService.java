package ma.itroad.ram.flightpass.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EmailService {
    void send(String to, String email, MultipartFile file);
    public void sendMessageUsingThymeleafTemplate(
            String to, String subject, Map<String, Object> templateModel, String template,MultipartFile file);

}
