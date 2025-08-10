package panzer.leopard2.openai2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {

    // LLM(OpenAI) - API Key이용하여 연결
    private final ChatModel chatModel;

    public String getResponse(String message) {
        return chatModel.call(message);
    }

    public String getResponseOptions(String message) {
        ChatResponse response = chatModel.call(new Prompt(
                message,
                OpenAiChatOptions.builder()
                        .model("gpt-5")
                        .temperature(1.0)
                        .build()
        ));

        log.info("response result : {}", response.getResult());
        return response.getResult().getOutput().getText();
    }
}
