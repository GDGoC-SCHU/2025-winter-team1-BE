package com.donut.donutproject.Dto;

import com.donut.donutproject.Entity.UserStudiedData;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 사용자 별 문제 풀이 기록 확인할 때
@Getter
@Setter
public class UserStudiedResponseDto {
    private String problem;
    private String correctAnswer;
    private String level;
    private String language;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserStudiedResponseDto(UserStudiedData userStudiedData) {
        this.problem = userStudiedData.getProblem();
        this.correctAnswer = userStudiedData.getCorrectAnswer();
        this.level = userStudiedData.getLevel();
        this.language = userStudiedData.getLanguage();
        this.createdAt = userStudiedData.getCreatedAt();
        this.modifiedAt = userStudiedData.getModifiedAt();
    }
}