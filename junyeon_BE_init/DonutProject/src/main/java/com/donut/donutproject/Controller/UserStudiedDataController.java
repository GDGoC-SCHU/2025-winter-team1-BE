package com.donut.donutproject.Controller;

import com.donut.donutproject.Dto.UserStudiedRequestDto;
import com.donut.donutproject.Dto.UserStudiedResponseDto;
import com.donut.donutproject.Entity.Users;
import com.donut.donutproject.Service.UserService;
import com.donut.donutproject.Service.UserStudiedService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study")
public class UserStudiedDataController {

    private final UserStudiedService userStudiedService;
    private final UserService userService;

    public UserStudiedDataController(UserStudiedService userStudiedDataService, UserService userService) {
        this.userStudiedService = userStudiedDataService;
        this.userService = userService;
    }

    // 사용자 문제 풀이 기록 조회
    @GetMapping("/history/{id}")
    public List<UserStudiedResponseDto> getUserHistory(@PathVariable Long id) {
        return userStudiedService.getSolvedProblems(id);
    }

    // 사용자 풀이 문제 정보 저장 -> submit 버튼 클릭 시 동작
    @PostMapping("/save")
    public UserStudiedResponseDto saveProblem(@RequestBody UserStudiedRequestDto requestDto) {
        return userStudiedService.saveProblem(requestDto);
    }

    // 문제 풀이 정답 제출 -> submit 버튼 클릭 시 동작 (정답 여부 확인 service 동작)
    @PutMapping("/submit/{id}") // @PathVariable Long id 추가
    public String submitProblem(@RequestBody UserStudiedRequestDto requestDto, @PathVariable Long id) {
        // SecurityContextHolder를 사용하여 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 로그인한 사용자의 username

        Users user = userService.findByUserId(id);

        boolean isCorrect = requestDto.getCorrectAnswer().equals(requestDto.getProblem()); // 문제와 정답 비교

        if (isCorrect) {
            userStudiedService.saveProblem(requestDto);
            return "정답입니다 !!";
        } else {
            return "틀렸습니다. 다시 시도해보세요 !";
        }
    }
}
