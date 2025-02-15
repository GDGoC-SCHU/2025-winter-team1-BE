package com.donut.donutproject.Controller;

import com.donut.donutproject.Service.GeminiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/generate-problem")
    public Mono<String> generateProblem(@RequestBody String difficulty) {
        return geminiService.generateCodingProblem(difficulty);
    }
}
