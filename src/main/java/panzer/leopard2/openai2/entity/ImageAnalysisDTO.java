package panzer.leopard2.openai2.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageAnalysisDTO {
    private String imageUrl;
    private String analysisText;
    private List<String> youtubeUrls; // 유튜브 URL 리스트 추가
}
