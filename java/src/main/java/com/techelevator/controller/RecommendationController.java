package com.techelevator.controller;

import com.techelevator.model.Plant;
import com.techelevator.service.OpenAiApi;
import com.techelevator.service.plantOpenAi.PlantOpenAi;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.utils.TikTokensUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class RecommendationController {

    private final OpenAiApi openAI;
    private final PlantOpenAi plantOpenAi;
    private final ControllerConsoleService console = new ControllerConsoleService("Open Ai Recommendations", Color.ORANGE);

    public RecommendationController(OpenAiApi openAi, PlantOpenAi plantOpenAi) {
        this.openAI = openAi;
        this.plantOpenAi = plantOpenAi;
    }


    @RequestMapping(path = "/recommendations", method = RequestMethod.POST)
    public List<ChatMessage> getPlantRecommendations(@RequestBody ChatMessage[] chatMessages) {
        String systemMsg = "Your name is the Garden Guru. You're a happy gnome. You are a master gardener. You are an expert in botany." +
                " You love plants and giving recommendations to users." +
                " You will give plant recommendations to the user based on where they live" +
                " and any other conditions they specify. Utilize lists when necessary and frequently. " +
                " If suggesting more than two plants to the user, put those in a list. " +
                " When responding, reply in simple" +
                " terms unless otherwise specified. Do reply with specific plants if possible. For indoor plant recommendations," +
                " include indoor lighting suggestions. Consider their expertise." +
                " Only provide information and recommendations relevant to " +
                " gardening and plants. Otherwise respond with 'I only provide plant and gardening information. {INSERT JOKE HERE}'";
        if (chatMessages.length == 1) {
            openAI.setSystemMessage(systemMsg);
        }
        console.log("Garden Care Guide - Approximate tokens: " + TikTokensUtil.tokens(TikTokensUtil.ModelEnum.GPT_3_5_TURBO.getName(), Arrays.asList(chatMessages)));
        return openAI.chatRequest(chatMessages);
    }

    @RequestMapping(path = "/care-guide", method = RequestMethod.POST)
    public List<ChatMessage> getGardenCareGuide(@RequestBody ChatMessage[] chatMessages) {
        String systemMsg = "Please provide the combined care schedule for the provided garden plants, formatted as follows:\n" +
                "\n" +
                "Watering: Detail the specific watering needs for each plant, including frequency and seasonal variations. Provide clear and concise information.\n" +
                "Sunlight: Summarize the sunlight requirements for all the plants, and highlight any specific needs.\n" +
                "Pruning: Outline the pruning schedule for each plant, including the time of year and specific techniques if available.\n" +
                "The response should be in markdown format, organized as a list with sub-points for each plant under the respective care categories" +
                " (Watering, Sunlight, Pruning). Please combine plants in the list if they have similar requirements. Include all plants requested if possible. Here's an example:\n" +
                "\n" +
                "### Watering\n" +
                "- **Plant Name 1:** Description of watering needs.\n" +
                "- **Plant Name 2:** Description of watering needs.\n" +
                "\n" +
                "### Sunlight\n" +
                "- **Plant Name 1:** Description of sunlight needs.\n" +
                "- **Plant Name 2:** Description of sunlight needs.\n" +
                "\n" +
                "### Pruning\n" +
                "- **Plant Name 1:** Description of pruning schedule.\n" +
                "- **Plant Name 2:** Description of pruning schedule.\n" +
                "\n" +
                "Please avoid including additional information or personal interpretations.";
        openAI.setSystemMessage(systemMsg);
//        console.log("Approximate tokens: " + (systemMsg + chatMessages[0].getContent()).length() / 4);
//        console.log(chatMessages[0].getContent());
        console.log("Garden Care Guide - Approximate tokens: " + TikTokensUtil.tokens(TikTokensUtil.ModelEnum.GPT_3_5_TURBO.getName(), systemMsg + chatMessages[0].getContent()));
        List<ChatMessage> response = new ArrayList<>();

        try {
            response = openAI.chatRequest(chatMessages);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, e.getCause().toString());
        }

        return response;
    }

    @RequestMapping(path = "/recommendations/plants", method = RequestMethod.POST)
    public List<ChatMessage> getPlantsFromRecommendation(@RequestBody String recommendation) {
        String firstResponse = plantOpenAi.getPlantSearchFromText(recommendation).toPrettyString();
        console.log(firstResponse);
        openAI.setSystemMessage(
                "You are an assistant that determines which plantId matches search word when referencing both" +
                " the body of text and the search results. Based on the following text you provided: " +
                recommendation + " Determine which search results match the plants you provided. " +
                "For each search result, return ONLY the closest matching one as an object" +
                " (with properties name and id) in an array. JSON format. " +
                "Example: [{\"id\": 1234, \"name\": \"Plant A\"},{\"id\": 2345, \"name\": \"Plant B\"},{\"id\": 3456, \"name\": \"Plant C\"},{\"id\": 4567, \"name\": \"Plant D\"}] Nothing else but the JSON code.");
        return openAI.chatRequest(openAI.createChatMessages(firstResponse));
//        return plantOpenAi.getPlantsFromSearchResults(firstResponse).plantsMap;
    }
}
