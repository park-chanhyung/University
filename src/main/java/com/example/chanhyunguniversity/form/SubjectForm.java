package com.example.chanhyunguniversity.form;

import com.example.chanhyunguniversity.domain.ProfessorEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectForm {
    @NotEmpty(message = "과목명은 필수항목입니다.")
    private String subjectName;

    @NotEmpty(message = "수업개요는 필수항목입니다.")
    private String classOverview;

    @NotEmpty(message = "수업 장소는 필수항목입니다.")
    private String classLocation;

    @NotEmpty(message = "수업 시간은 필수항목입니다.")
    private String classTime;

    @NotEmpty(message = "학점은 필수항목입니다.")
    private String credits;

    @NotEmpty(message = "교과목 번호는 필수항목입니다.")
    private String classNumber;

    @NotEmpty(message = "분류는 필수항목입니다.")
    private String classification;

    @NotNull(message = "수강 정원은 필수항목입니다.")
    @Min(value = 1, message = "수강 정원은 1 이상이어야 합니다.")
    private Integer totalCapacity;

    @NotEmpty(message = "교과목 학년은 필수항목입니다.")
    private String subjectGrade;

    @NotEmpty(message = "담당 교수는 필수항목입니다.")
    private String professorName;


    private String department;

    public String getProfessorName() {
        return professorName;
    }


    public void setProfessor(ProfessorEntity professor) {
    }
}
