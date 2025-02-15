package com.donut.donutproject.Dto;

import com.donut.donutproject.Entity.UserStudiedData;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserStudiedResponseDto {
    private String problem;
    private String answer;
    private String difficulty;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserStudiedResponseDto(UserStudiedData userStudiedData) {
        this.problem = userStudiedData.getProblem();
        this.answer = userStudiedData.getAnswer();
        this.difficulty = userStudiedData.getDifficulty();
        this.createdAt = userStudiedData.getCreatedAt();
        this.modifiedAt = userStudiedData.getModifiedAt();
    }
}
