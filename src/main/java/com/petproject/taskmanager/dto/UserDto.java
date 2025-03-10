package com.petproject.taskmanager.dto;

public record UserDto(
    String id,
    String username,
    String email,
    String password) {

}
