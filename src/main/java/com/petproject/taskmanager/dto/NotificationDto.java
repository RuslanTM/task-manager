package com.petproject.taskmanager.dto;

public record NotificationDto(
    String recipient,
    String subject,
    String message
) {

}
