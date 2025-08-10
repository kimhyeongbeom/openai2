package panzer.leopard2.openai2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import panzer.leopard2.openai2.service.ChatService;

@RestController
@RequiredArgsConstructor
public class AskController {

    private final ChatService chatService;

    @GetMapping("/ask")
    public String getResponse(String message) {
        return chatService.getResponse(message);
    }

    @GetMapping("/ask-ai")
    public String getResponseOptions(String message) {
        return chatService.getResponseOptions(message);
    }
}
