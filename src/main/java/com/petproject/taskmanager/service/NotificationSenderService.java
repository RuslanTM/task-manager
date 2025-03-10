package com.petproject.taskmanager.service;

import com.petproject.taskmanager.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSenderService {

    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;
    @Value("${spring.kafka.topic.name}")
    private String topic;

    public void send(String to, String message, String subject) {
        var notification = new NotificationDto(to, subject, message);
        kafkaTemplate.send(topic, notification);
    }
}
