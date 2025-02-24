package com.donut.donutproject.Controller;

import com.donut.donutproject.Dto.UserStudiedResponseDto;
import com.donut.donutproject.Service.UserStudiedService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MypageController {
    private final UserStudiedService userStudiedService;

    public MypageController(UserStudiedService userStudiedService) {
        this.userStudiedService = userStudiedService;
    }

    @GetMapping("/mypage")
    public String mypage(Model model) { // 반환 타입을 String으로 변경하고 Model 추가
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<UserStudiedResponseDto> solvedProblems = userStudiedService.getSolvedProblems(username);
        model.addAttribute("solvedProblems", solvedProblems); // Model에 데이터 추가

        return "mypage"; // 뷰 이름 반환
    }
}
