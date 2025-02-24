package com.donut.donutproject.Controller;

import com.donut.donutproject.Service.GeminiService;
import com.donut.donutproject.Service.UserStudiedService;
import org.springframework.web.bind.annotation.*;

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

    // 문제 풀이 정답 제출 -> submit 버튼 클릭 시 동작 (정답 여부 확인 service 동작) -> 반환값 true 면 정답 false 면 오답
    // 서버에서 정답 여부 팝업창으로 띄우기
    @PostMapping("/submit")
    public boolean submitAnswer(@RequestParam Long problemId, @RequestBody Map<String, String> payload) {
        String userAnswer = payload.get("userAnswer");
        return geminiService.checkAnswer(problemId, userAnswer);
    }
}