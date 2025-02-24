package com.donut.donutproject.Dto;

import com.donut.donutproject.Entity.UserStudiedData;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 사용자 별 문제 풀이 기록 확인할 때
@Getter
@Setter
public class UserStudiedResponseDto {
    private String problem;
    private String correctAnswer;
    private String level;
    private String language;
    private String createdAt;
    private String modifiedAt;

    public UserStudiedResponseDto(UserStudiedData userStudiedData) {
        this.problem = userStudiedData.getProblem();
        this.correctAnswer = userStudiedData.getCorrectAnswer();
        this.level = userStudiedData.getLevel();
        this.language = userStudiedData.getLanguage();
        this.createdAt = formatDateTime(userStudiedData.getCreatedAt()); // formatDateTime 사용
        this.modifiedAt = formatDateTime(userStudiedData.getModifiedAt()); // formatDateTime 사용
    }

    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}