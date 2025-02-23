package com.donut.donutproject.Service;

import com.donut.donutproject.Dto.GeminiResponseDto;
import com.donut.donutproject.Dto.ProblemRequestDto;
import com.donut.donutproject.Entity.UserStudiedData;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiApiService {
    private static final Dotenv dotenv = Dotenv.load();
    private final String geminiApiUrl = dotenv.get("GEMINI_API_URL");
    private final String geminiApiKey = dotenv.get("GEMINI_API_KEY");

    private final RestTemplate restTemplate = new RestTemplate();
    // 로그 확인용 -> 해결되면 삭제
    private final Logger logger = LoggerFactory.getLogger(GeminiApiService.class);

    public UserStudiedData generateProblem(ProblemRequestDto problemRequestDto) {
        // 로그 확인용
        logger.info("Gemini API Key: {}", geminiApiKey);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", geminiApiKey);
            headers.set("Content-Type", "application/json");

            logger.info("Request Headers: {}", headers); // 요청 헤더 로깅 -> 해결 시 삭제

            String prompt = String.format("Generate a coding problem with difficulty '%s' in '%s' language, and provide the correct answer.",
                    problemRequestDto.getLevel(), problemRequestDto.getLanguage());

            HttpEntity<String> requestEntity = new HttpEntity<>(prompt, headers);

            logger.info("Sending API request to: {}", geminiApiUrl); // API 호출 전 로깅

            ResponseEntity<GeminiResponseDto> responseEntity = restTemplate.exchange(
                    geminiApiUrl, HttpMethod.POST, requestEntity, GeminiResponseDto.class);

            logger.info("API request successful. Response status: {}", responseEntity.getStatusCode()); // API 호출 후 로깅

            logger.info("API Response status: {}", responseEntity.getStatusCode()); // 응답 상태 코드 로깅
            logger.info("API Response body: {}", responseEntity.getBody()); // 응답 본문 로깅

            GeminiResponseDto response = responseEntity.getBody();
            String problem = response.getProblem();
            String correctAnswer = response.getCorrectAnswer();

            UserStudiedData studiedData = new UserStudiedData();
            studiedData.setProblem(problem);
            studiedData.setCorrectAnswer(correctAnswer);
            studiedData.setLevel(problemRequestDto.getLevel());
            studiedData.setLanguage(problemRequestDto.getLanguage());

            return studiedData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
