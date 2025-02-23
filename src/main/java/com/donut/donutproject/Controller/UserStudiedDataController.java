package com.donut.donutproject.Controller;

import com.donut.donutproject.Dto.UserStudiedResponseDto;
import com.donut.donutproject.Service.GeminiService;
import com.donut.donutproject.Service.UserStudiedService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/study")
public class UserStudiedDataController {

    private final UserStudiedService userStudiedService;
    private final GeminiService geminiService;

    public UserStudiedDataController(UserStudiedService userStudiedDataService, GeminiService geminiService) {
        this.userStudiedService = userStudiedDataService;
        this.geminiService = geminiService;
    }

    // 사용자 문제 풀이 기록 조회
//    @GetMapping("/history")
//    public List<UserStudiedResponseDto> getUserHistory() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // UserDetails 객체에서 username (아이디) 값을 가져오기
//        String username = authentication.getName();
//
//        return userStudiedService.getSolvedProblems(username);
//    }

    // 문제 풀이 정답 제출 -> submit 버튼 클릭 시 동작 (정답 여부 확인 service 동작) -> 반환값 true 면 정답 false 면 오답
    // 서버에서 정답 여부 팝업창으로 띄우기
    @PostMapping("/submit")
    public boolean submitAnswer(@RequestParam Long problemId, @RequestBody Map<String, String> payload) {
        String userAnswer = payload.get("userAnswer");
        return geminiService.checkAnswer(problemId, userAnswer);
    }
}