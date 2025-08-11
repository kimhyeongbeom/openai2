package panzer.leopard2.openai2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class FileDownloadController {
    private final RestTemplate restTemplate;

    @GetMapping("/download-file")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String url) {
        try{
            URI uri = new URI(url);
            ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);

            String filename = extractFileName(url);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ("Failed to download file: " + e.getMessage()).getBytes()
            );
        }
    }

    private String extractFileName(String url) {
        String path = URI.create(url).getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
