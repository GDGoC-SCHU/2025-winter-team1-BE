package com.donut.donutproject.Service;

import com.donut.donutproject.Repository.UserStudiedDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeminiService {

    private final WebClient webClient;
    private final UserStudiedDataRepository studiedDataRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiService(WebClient.Builder webClientBuilder, UserStudiedDataRepository studiedDataRepository) {
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro")
                .build();
        this.studiedDataRepository = studiedDataRepository;
    }

    public Mono<String> generateCodingProblem(String difficulty) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/generate")
                        .queryParam("difficulty", difficulty)
                        .build())
                .header("Authorization", "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(String.class);
    }
}

