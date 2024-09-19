package com.example.chanhyunguniversity.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false)
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

    @Column
    private int ableCredits;

    @Column
    private int totalCredits = 0; // 신청 학점

    @OneToMany(mappedBy = "student")
    private List<CourseRegistrationEntity> registrations;


    public void addCredits(int credits) throws IllegalStateException{
        if (totalCredits + credits > ableCredits){
            throw new IllegalStateException("신청 가능한 학점을 초과했습니다. ");
        }
        this.totalCredits += credits;
    }
    public void removeCredits(int credits){
        this.totalCredits = Math.max(0,this.totalCredits - credits);
    }
}

