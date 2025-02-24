package com.donut.donutproject.Service;

import com.donut.donutproject.Dto.ProblemRequestDto;
import com.donut.donutproject.Entity.UserStudiedData;
import com.donut.donutproject.Repository.UserStudiedDataRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final UserStudiedDataRepository studiedDataRepository;

    public GeminiService(UserStudiedDataRepository studiedDataRepository) {
        this.studiedDataRepository = studiedDataRepository;
    }

    // 문제 저장
    public void save(ProblemRequestDto problemRequestDto) {
        UserStudiedData userStudiedData = new UserStudiedData(problemRequestDto);
        studiedDataRepository.save(userStudiedData);
    }

    public boolean checkAnswer(Long problemId, String userAnswer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserStudiedData userStudiedData = studiedDataRepository.findByProblemIdAndUsername(problemId, username)
                .orElseThrow(() -> new IllegalArgumentException("문제를 찾을 수 없습니다. problemId: " + problemId + ", username: " + username));
        return userStudiedData.getCorrectAnswer().equals(userAnswer);
    }
}

