package com.donut.donutproject.Controller;

import com.donut.donutproject.Dto.ProblemRequestDto;
import com.donut.donutproject.Service.GeminiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GeminiController {

    private static final Dotenv dotenv = Dotenv.load();
    private final String geminiApiUrl = dotenv.get("GEMINI_API_URL");
    private final String apiKey = dotenv.get("GEMINI_API_KEY");
    private final GeminiService geminiService;
    private final RestTemplate restTemplate = new RestTemplate();

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/submit")
    public boolean submitProblem(@RequestParam Long problemId, @RequestParam String userAnswer) {
        return geminiService.checkAnswer(problemId, userAnswer);
    }

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generate(@RequestBody Map<String, String> requestPayload) {
        String level = requestPayload.get("level");
        String language = requestPayload.get("language");

        try {
            String question = generateQuestion(level, language);
            String answer = predictAnswer(question);
            // 문제 텍스트 파싱 및 HTML 변환
            String formattedQuestion = formatQuestion(question);

            Map<String, String> result = new HashMap<>();
            //result.put("문제", question);
            result.put("문제", formattedQuestion);
            result.put("답", answer);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            ProblemRequestDto problemRequestDto = new ProblemRequestDto(username, question, answer, level, language);
            geminiService.save(problemRequestDto);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "문제 생성 실패: " + e.getMessage()));
        }
    }

    private String formatQuestion(String question) {
        // 줄바꿈 문자를 <br> 태그로 변환
        String formattedQuestion = question.replace("\n", "<br>");

        // 코드 블록을 <pre><code> 태그로 감싸기
        formattedQuestion = formattedQuestion.replace("```python", "<pre><code>python");
        formattedQuestion = formattedQuestion.replace("```java", "<pre><code>java");
        formattedQuestion = formattedQuestion.replace("```javascript", "<pre><code>javascript");
        formattedQuestion = formattedQuestion.replace("```", "</code></pre>");

        return formattedQuestion;
    }

    private String generateQuestion(String level, String language) throws Exception {
        String prompt = level + " " + language + " 빈칸 문제를 만들어줘. 정답은 알려주지마.";
        return sendGeminiRequest(prompt);
    }

    private String predictAnswer(String question) throws Exception {
        String prompt = "다음 문제의 빈칸(____)에 들어갈 정답 **하나만 출력해**.\n"
                + "다른 문장은 절대 포함하지 마.\n"
                + "빈칸에 들어갈 **정확한 단어 또는 코드**만 말해.\n"
                + "예제: `+=`, `15`, `print` 같은 단어만 출력해.\n\n" + question;
        return cleanAnswer(sendGeminiRequest(prompt));
    }

    private String sendGeminiRequest(String prompt) throws Exception {
        Map<String, Object> requestBody = createGeminiRequest(prompt);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey); // API 키 헤더에 포함

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                geminiApiUrl, HttpMethod.POST, entity, String.class);

        return extractText(response.getBody());
    }

    private Map<String, Object> createGeminiRequest(String prompt) {
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> contents = new HashMap<>();
        Map<String, Object> parts = new HashMap<>();
        parts.put("text", prompt);
        contents.put("parts", parts);
        contents.put("role", "user");
        request.put("contents", contents);

        Map<String, Object> systemInstruction = new HashMap<>();
        Map<String, Object> systemParts = new HashMap<>();
        systemParts.put("text", "문제 내용은 문장으로\\n"
                + "빈칸 문제\\n"
                + "만 적어줘\\n"
                + "코딩 언어는 내가 지정해줄거야.\\n"
                + "단계는 왕초급, 초급, 중급, 고급이 있어.\\n"
                + "난이도는 너가 적절히 일관성있게 적용해야해.\\n"
                + "네 같은 대답할 필요는 없어.\\n"
                + "문제는 1개야, 정답도 빈칸은 1개로하여 정답도 하나가 나오게 할거야\\n"
                + "답은 알려주지말아봐\\n"
                + "문제만 출제해");
        systemInstruction.put("parts", systemParts);
        systemInstruction.put("role", "user");
        request.put("systemInstruction", systemInstruction);

        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("temperature", 1);
        generationConfig.put("topK", 40);
        generationConfig.put("topP", 0.95);
        generationConfig.put("maxOutputTokens", 8192);
        generationConfig.put("responseMimeType", "text/plain");
        request.put("generationConfig", generationConfig);

        return request;
    }

    private String extractText(String response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode candidates = rootNode.path("candidates");

        if (candidates.isArray() && candidates.size() > 0) {
            JsonNode parts = candidates.get(0).path("content").path("parts");
            if (parts.isArray() && parts.size() > 0) {
                return parts.get(0).path("text").asText().trim();
            }
        }
        throw new Exception("Gemini API 응답에서 텍스트 추출 실패");
    }

    private String cleanAnswer(String answer) {
        answer = answer.replaceAll("왕초급", "").trim();
        if (answer.contains("의 값은?")) {
            answer = answer.split("의 값은?")[0].trim();
        }
        if (answer.contains("\n")) {
            return answer.split("\n")[0].trim();
        }
        return answer.trim();
    }
}