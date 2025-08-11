package panzer.leopard2.openai2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import panzer.leopard2.openai2.entity.ImageAnalysisDTO;
import panzer.leopard2.openai2.service.AnalyzeService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzeService analyzeService;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/analyze")
    public ResponseEntity<ImageAnalysisDTO> getMultimodalResponse(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam(defaultValue = "이 이미지에 무엇이 있나요?") String message)
            throws IOException {

        // Ensure the upload directory exists
        File uploadDirectory = new File(uploadPath);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs(); // uploads
        }

        // Save the uploaded file to the specified upload path
        String filename = imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadPath, filename);
        Files.write(filePath, imageFile.getBytes()); // 업로드

        // Analyze the image
        String analysisText = analyzeService.analyzeImage(imageFile, message);
        String imageUrl = "/uploads/" + filename;

        ImageAnalysisDTO response = new ImageAnalysisDTO(imageUrl, analysisText, null);
        return ResponseEntity.ok(response);  //  { "imageUrl":".....", "analysisText":".........." }
    }

    @PostMapping("/solve")
    public ResponseEntity<ImageAnalysisDTO> solveResponse(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam(defaultValue = "이 이미지에 무엇이 있나요?") String message)
            throws Exception {
        // Ensure the upload directory exists
        File uploadDirectory = new File(uploadPath);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        // Save the uploaded file to the specified upload path
        String filename = imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadPath, filename);
        Files.write(filePath, imageFile.getBytes());

        // Analyze the image
        String analysisText = analyzeService.solveImage(imageFile, message);
        // 세제곱근, 제곱근, 곱셈
        String searchKeyword = analyzeService.extractKeyYouTubeSearch(analysisText);
        List<String> youtubeUrls = analyzeService.searchYouTubeVideos(searchKeyword);

        String imageUrl = "/uploads/" + filename;

        ImageAnalysisDTO response = new ImageAnalysisDTO(imageUrl, analysisText, youtubeUrls);
        return ResponseEntity.ok(response);
    }
}
