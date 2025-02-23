package com.donut.donutproject.Service;

import com.donut.donutproject.Dto.ProblemRequestDto;
import com.donut.donutproject.Dto.ProblemResponseDto;
import com.donut.donutproject.Entity.UserStudiedData;
import com.donut.donutproject.Repository.UserStudiedDataRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final UserStudiedDataRepository studiedDataRepository;
    private final GeminiApiService geminiApiService;

    public GeminiService(UserStudiedDataRepository studiedDataRepository, GeminiApiService geminiApiService) {
        this.studiedDataRepository = studiedDataRepository;
        this.geminiApiService = geminiApiService;
    }

    // 새로 추가
    public void save(ProblemRequestDto problemRequestDto) {
        UserStudiedData userStudiedData = new UserStudiedData(problemRequestDto);
        studiedDataRepository.save(userStudiedData);
    }

//    public UserStudiedData generateCodingProblem(ProblemRequestDto problemRequestDto) {
//        UserStudiedData studiedData = geminiApiService.generateProblem(problemRequestDto);
//
//        if (studiedData != null) {
//            studiedDataRepository.save(studiedData);
//        }
//
//        return studiedData;
//    }

//    public boolean checkAnswer(Long problemId, String userAnswer) {
//        // problemId를 사용하여 데이터베이스에서 정답 조회
//        UserStudiedData userStudiedData = studiedDataRepository.findById(problemId).orElse(null);
//
//        ProblemResponseDto responseDto = new ProblemResponseDto(userStudiedData);
//
//        // 정답 비교
//        return responseDto != null && responseDto.getCorrectAnswer().equals(userAnswer);
//    }

    public boolean checkAnswer(Long problemId, String userAnswer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserStudiedData userStudiedData = studiedDataRepository.findByProblemIdAndUsername(problemId, username)
                .orElseThrow(() -> new IllegalArgumentException("문제를 찾을 수 없습니다. problemId: " + problemId + ", username: " + username));
        return userStudiedData.getCorrectAnswer().equals(userAnswer);
    }
}

