package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    String chat(@RequestParam String question) {
        return chatClient
                .prompt(question)
                .call()
                .content();
    }

    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam String question) {
        return chatClient
                .prompt(question)
                .options(ChatOptionsBuilder.builder()
                        .withModel(MistralAiApi.ChatModel.OPEN_MIXTRAL_7B.getName())
                        .withTemperature(0.9)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/provider-options")
    String chatWithProviderOptions(@RequestParam String question) {
        return chatClient
                .prompt(question)
                .options(MistralAiChatOptions.builder()
                        .withSafePrompt(true)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(@RequestParam String question) {
        return chatClient
                .prompt(question)
                .stream()
                .content();
    }

}
