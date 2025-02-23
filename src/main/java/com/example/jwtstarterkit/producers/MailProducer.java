package com.example.jwtstarterkit.producers;

import com.example.jwtstarterkit.dtos.EmailDetailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;

    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;

    @Autowired
    public MailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMailToQueue(EmailDetailDTO emailDetailDTO) {
        rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, emailDetailDTO);
        System.out.println("E-posta kuyruğa eklendi: " + emailDetailDTO.getTo());
    }
}
