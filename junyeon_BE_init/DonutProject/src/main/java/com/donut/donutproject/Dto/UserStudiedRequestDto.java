package com.donut.donutproject.Dto;

import com.donut.donutproject.Entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserStudiedRequestDto {
    private Long userId; // 사용자 ID

    private String problem; // 문제 내용

    private String correctAnswer; // 정답

    private String difficulty; // 문제 난이도

    public UserStudiedRequestDto(Long userId, String problem, String correctAnswer, String difficulty) {
        this.userId = userId;
        this.problem = problem;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }
}
