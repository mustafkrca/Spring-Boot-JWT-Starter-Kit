package com.example.jwtstarterkit.services;

import com.example.jwtstarterkit.dtos.EmailDetailDTO;
import com.example.jwtstarterkit.utils.MailSenderUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

@Service
public class MailConsumer {

    @Autowired
    private MailSenderUtil mailSenderUtil;

    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void sendMail(EmailDetailDTO emailDetailDTO) {
        String to = emailDetailDTO.getTo();
        String subject = emailDetailDTO.getSubject();
        String htmlContent = emailDetailDTO.getHtmlContent();

        try {
            mailSenderUtil.sendHtmlMail(to, subject, htmlContent);
            System.out.println("E-posta başarıyla gönderildi: " + to);
        } catch (Exception e) {
            System.err.println("E-posta gönderme hatası: " + e.getMessage());
        }
    }
}

