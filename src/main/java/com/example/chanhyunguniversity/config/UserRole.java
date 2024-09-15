package com.example.chanhyunguniversity.config;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    student("ROLE_student");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}