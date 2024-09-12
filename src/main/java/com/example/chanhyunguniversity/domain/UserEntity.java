package com.example.chanhyunguniversity.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String name;

    @Column
    private String password;

    @Column(nullable = false)
    private String role;

    @Column
    private String grade;

    @Column
    private String department;

    @Column
    private String birthday;

    @Column
    private String gender;

    @Column
    private LocalDateTime createdAt;
}
