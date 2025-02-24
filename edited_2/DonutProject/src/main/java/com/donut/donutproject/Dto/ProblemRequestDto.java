package com.donut.donutproject.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProblemRequestDto {
    private String username;
    private String problem;
    private String correctAnswer;
    private String level;
    private String language;
}