package panzer.leopard2.openai2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;
import panzer.leopard2.openai2.entity.ImageRequestDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final OpenAiImageModel openAiImageModel;

    public ImageResponse getImageGen(ImageRequestDTO requestDTO) {
        ImageResponse imageResponse =  openAiImageModel
                .call(new ImagePrompt(requestDTO.getMessage(),
                        OpenAiImageOptions.builder()
                                .model(requestDTO.getModel())
                                .quality("hd")
                                .N(requestDTO.getN())
                                .height(1024)
                                .width(1024).build()));
        return imageResponse;
    }
}
