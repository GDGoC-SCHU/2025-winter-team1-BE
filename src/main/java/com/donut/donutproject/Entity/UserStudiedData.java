package com.donut.donutproject.Entity;

import com.donut.donutproject.Dto.ProblemRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "studied")
public class UserStudiedData extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId; // 문제 id

    @Column(name = "user_id", nullable = false)
    private String username; // 사용자 id

    @Column(length = 10000)
    private String problem; // 문제 내용

    private String correctAnswer; // 정답

    private String level; // 난이도

    private String language;

    public UserStudiedData(ProblemRequestDto problemRequestDto) {
        this.username = problemRequestDto.getUsername();
        this.problem = problemRequestDto.getProblem();
        this.correctAnswer = problemRequestDto.getCorrectAnswer();
        this.level = problemRequestDto.getLevel();
        this.language = problemRequestDto.getLanguage();
    }
}
