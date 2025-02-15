package com.donut.donutproject.Entity;

import com.donut.donutproject.Dto.UserStudiedRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "studied")
public class UserStudiedData extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 id

    private String problem; // 문제 내용

    private String answer; // 정답

    private String difficulty; // 난이도

    public UserStudiedData(UserStudiedRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.problem = requestDto.getProblem();
        this.answer = requestDto.getCorrectAnswer();
        this.difficulty = requestDto.getDifficulty();
    }
}
