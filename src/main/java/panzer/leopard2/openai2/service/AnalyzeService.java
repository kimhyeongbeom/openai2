package panzer.leopard2.openai2.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzeService {
    private final ChatModel chatModel;

    @Value("classpath:/system.message")
    private Resource defaultSystemMessage;

    @Value("classpath:/systemMath.message")
    private Resource systemMathMessage;

    public String analyzeImage(MultipartFile imageFile, String message) {
        // MIME 타입 결정
        String contentType = imageFile.getContentType();
        if (!MimeTypeUtils.IMAGE_PNG_VALUE.equals(contentType) &&
                !MimeTypeUtils.IMAGE_JPEG_VALUE.equals(contentType)) {
            throw new IllegalArgumentException("지원되지 않는 이미지 형식입니다.");
        }
        try {
            // Media 객체 생성
            var media = new Media(MimeType.valueOf(contentType), imageFile.getResource());
            // 사용자 메시지 생성
            var userMessage = UserMessage.builder()
                    .text(message)
                    .media(List.of(media))
                    .build();
            // 시스템 메시지 생성
            var systemMessage = new SystemMessage(defaultSystemMessage);
            // AI 모델 호출
            return chatModel.call(userMessage, systemMessage);
        } catch (Exception e) {
            throw new RuntimeException("이미지 처리 중 오류가 발생했습니다.", e);
        }
    }

    public String solveImage(MultipartFile imageFile, String message) throws IOException {
        String contentType = imageFile.getContentType();
        if (!MimeTypeUtils.IMAGE_PNG_VALUE.equals(contentType) &&
                !MimeTypeUtils.IMAGE_JPEG_VALUE.equals(contentType)) {
            throw new IllegalArgumentException("지원되지 않는 이미지 형식입니다.");
        }

        var media = new Media(MimeType.valueOf(contentType), imageFile.getResource());
        var userMessage = UserMessage.builder()
                .text(message)
                .media(List.of(media))
                .build();
        var systemMessage = new SystemMessage(systemMathMessage);
        return chatModel.call(userMessage, systemMessage);
    }
    //                                                                         query=EBS 세제곱근, 제곱근, 곱셈
    public List<String> searchYouTubeVideos(String query) throws Exception {
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&q=EBS " +
                query + "&order=relevance&key=AIzaSyA4KW65sAMv1ziOmr4-qMuJHEL9ivH_5YY";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.getBody());

        List<String> videoUrls = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray items = jsonResponse.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String videoId = item.getJSONObject("id").getString("videoId");
            videoUrls.add(videoId);
        }
        return videoUrls;
    }

    public String extractKeyYouTubeSearch(String analysisText) {
        String keyword=null;
        if(analysisText.indexOf("핵심 키워드:")!=-1){
            //                                                                    핵심 키워드: 세제곱근, 제곱근, 곱셈
            keyword=analysisText.substring(analysisText.indexOf("핵심 키워드:")).split(":")[1].trim();
        }
        //          세제곱근, 제곱근, 곱셈
        return keyword;
    }
}
