package com.ebook.order_service.Service;

import com.ebook.order_service.Config.RabbitMQConfig;
import com.ebook.order_service.DTO.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishEmailMessage(String to, String subject, String body) {
        EmailMessage emailMessage = new EmailMessage(to, subject, body);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, emailMessage);
        log.info("Email message published to RabbitMQ for: {}", to);
    }
}
