package com.techelevator.service;

import com.techelevator.controller.ControllerConsoleService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class OpenAiApi {
    private String apiKey;
    private String systemMessage;
    private String userMessage;
    private final String MODEL = "gpt-3.5-turbo-16k";
    private int maxTokens = 4000;
    private OpenAiService service;
    private Duration timeout = Duration.ofSeconds(60);

    public OpenAiApi(@Value("${openai.key}") String apiKey) {
        this.apiKey = apiKey;
        this.service = new OpenAiService(apiKey, timeout);
    }

    public ChatMessage chatRequest() {
        List<ChatMessage> messages = new ArrayList<>();
        // Added for debugging
        if (service == null) {
            this.service = new OpenAiService(apiKey, timeout);
        }

        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), userMessage));
        ChatCompletionRequest chatRequest = ChatCompletionRequest
                .builder()
                .model(MODEL)
                .messages(messages)
                .n(1)
                .maxTokens(maxTokens)
                .build();

        return service.createChatCompletion(chatRequest).getChoices().get(0).getMessage();
    }

    public List<ChatMessage> chatRequest(ChatMessage[] chatMessages) {
        List<ChatMessage> messages = new ArrayList<>();
        // Added for debugging
        if (service == null) {
            this.service = new OpenAiService(apiKey, timeout);
        }

        // The beginning of a chat with user should bundle the user
        if (chatMessages.length == 1) {
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage));
            messages.add(chatMessages[0]);
        } else {
            messages.addAll(Arrays.asList(chatMessages));
        }
        ChatCompletionRequest chatRequest = ChatCompletionRequest
                .builder()
                .model(MODEL)
                .messages(messages)
                .n(1)
                .maxTokens(maxTokens)
                .build();
        ControllerConsoleService console = new ControllerConsoleService("Open Ai Recommendations", Color.ORANGE);
        int requestLength = Math.min(messages.get(messages.size() - 1).getContent().length(), 100);
        console.log("Request: " + messages.get(messages.size() - 1).getContent().substring(0, requestLength) + "...");
        ChatMessage response = service.createChatCompletion(chatRequest).getChoices().get(0).getMessage();
        messages.add(response);
        int responseLength = Math.min(response.getContent().length(), 100);
        console.log("Response: " + response.getContent().substring(0, responseLength) + "...");
        return messages;
        //return messages instead
    }

    public ChatMessage[] createChatMessages(String message) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatMessageRole.USER.value(), message));
        return chatMessages.toArray(new ChatMessage[0]);
    }


    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }
}
