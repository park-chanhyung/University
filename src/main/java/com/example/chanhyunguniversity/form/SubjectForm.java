package com.example.chanhyunguniversity.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectForm {
    @NotEmpty(message = "subjectName 필수항목입니다.")
    private String subjectName;

    @NotEmpty(message = "classOverview 필수항목입니다.")
    private String classOverview; // 수업개요

    @NotEmpty(message = "classLocation 필수항목입니다.")
    private String classLocation; // 수업 장소

    @NotEmpty(message = "classTime 필수항목입니다.")
    private String classTime; // 수업 시간

    @NotEmpty(message = "credits 필수항목입니다.")
    private String credits; // 학점

    @NotEmpty(message = "classNumber 필수항목입니다.")
    private String classNumber; // 교과목 번호
}
