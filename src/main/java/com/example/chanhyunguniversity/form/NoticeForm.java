package com.example.chanhyunguniversity.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeForm {

    @NotEmpty(message = "공지사항 제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "공지사항 내용을 입력해주세요.")
    private String content;

    private boolean fixNotice;

}
