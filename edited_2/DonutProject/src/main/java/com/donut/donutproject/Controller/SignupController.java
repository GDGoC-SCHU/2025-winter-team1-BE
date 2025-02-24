package com.donut.donutproject.Controller;

import com.donut.donutproject.Dto.AddUserRequest;
import com.donut.donutproject.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user")
    public String signup(@RequestBody AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }
}
