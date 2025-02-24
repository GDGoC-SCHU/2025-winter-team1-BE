package com.donut.donutproject.Dto;

import com.donut.donutproject.Entity.UserStudiedData;
import lombok.Getter;

import java.time.LocalDateTime;

// 문제 생성 시 데이터를 서버에 전달하는데 사용
@Getter
public class ProblemResponseDto {
    private String problem;
    private String correctAnswer;
    private String level;
    private String language;

    public ProblemResponseDto(UserStudiedData userStudiedData) {
        this.problem = userStudiedData.getProblem();
        this.correctAnswer = userStudiedData.getCorrectAnswer();
        this.level = userStudiedData.getLevel();
        this.language = userStudiedData.getLanguage();
    }
}
