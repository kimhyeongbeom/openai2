package panzer.leopard2.openai2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import panzer.leopard2.openai2.entity.ImageRequestDTO;
import panzer.leopard2.openai2.service.ImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageGenerationController {
    private final ImageService imageService;

    @PostMapping(value = "/image", consumes = "application/json; charset=UTF-8")
    public List<String> image(@RequestBody ImageRequestDTO requestDTO) throws IOException {
        ImageResponse imageResponse = imageService.getImageGen(requestDTO);

        List<String> imageUrls=imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();

        return  imageUrls;
    }
}
