package com.techelevator.service.plantOpenAi;

import com.fasterxml.jackson.databind.JsonNode;
import com.techelevator.model.Plant;
import com.techelevator.service.PlantApi;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class PlantOpenAi {

    private PlantApi plantService;
    private String openAiKey;
    private final String MODEL = "gpt-3.5-turbo-16k";
    private int maxTokens = 2000;
    Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);

    public PlantOpenAi(@Value("${plants.key}") String plantKey, @Value("${openai.key}") String openAiKey) {
        this.plantService = new PlantApi(plantKey);
        this.openAiKey = openAiKey;
    }

    public JsonNode getPlantSearchFromText(String text) {
        OpenAiService openAiService = new OpenAiService(openAiKey, DEFAULT_TIMEOUT);
        String systemRoleMsg = "You are an assistant that parses text to determine which plants should be searched for (if any) Search words should not be plural. " +
                "Example: If given the following text to parse: \"Another lovely blue flower to consider is the forget-me-not (Myosotis). They are delicate, dainty flowers with soft blue" +
                " petals that create a carpet-like effect when planted in mass. Forget-me-nots are annual or perennial plants, depending on the variety," +
                " and they prefer moist soil and partial shade.\" Return a search word like: \"forget-me-not\" OR maybe \"myosotis\" but NOT just the word \"blue\".";
        FunctionExecutor functionExecutor = new FunctionExecutor(Collections.singletonList(ChatFunction.builder()
                .name("getPlantsBySearch")
                .description("Search for plants by name")
                .executor(PlantSearchAi.class, s -> {
                    Map<String, List<Plant>> plantsMap = new HashMap<>();
                    for (String searchWord : s.searchWords) {
                        plantsMap.put(searchWord, plantService.getPlantsBySearch(searchWord));
                    }
                    return new PlantSearchAiResponse(plantsMap);
                })
                .build()));
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemRoleMsg);
        messages.add(systemMessage);

        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), text);
        messages.add(firstMsg);

        return buildJsonFromExecutedFunctions(messages, functionExecutor, openAiService);
    }

    public PlantSearchAiResponse getPlantSearchMapFromText(String text) {
        OpenAiService openAiService = new OpenAiService(openAiKey, DEFAULT_TIMEOUT);
        String systemRoleMsg = "You are an assistant that parses text to determine which plants should be searched for (if any).";
        FunctionExecutor functionExecutor = new FunctionExecutor(Collections.singletonList(ChatFunction.builder()
                .name("getPlantsBySearch")
                .description("Search for plants by name")
                .executor(PlantSearchAi.class, s -> {
                    Map<String, List<Plant>> plantsMap = new HashMap<>();
                    for (String searchWord : s.searchWords) {
                        plantsMap.put(searchWord, plantService.getPlantsBySearch(searchWord));
                    }
                    return new PlantSearchAiResponse(plantsMap);
                })
                .build()));
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemRoleMsg);
        messages.add(systemMessage);

        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), text);
        messages.add(firstMsg);

        return buildPSARFromExecutedFunctions(messages, functionExecutor, openAiService);
    }

    public PlantMap getPlantsFromSearchResults(String text) {
        OpenAiService openAiService = new OpenAiService(openAiKey, DEFAULT_TIMEOUT);
        String systemRoleMsg = "You are an assistant that determines which plantId matches search word when referencing both the body of text and the search results. Ex:" +
                "Body search words derived from: " +
                "1. Hosta - This shade-loving plant has lovely foliage and thrives in areas with limited sunlight. " +
                "2. Japanese Painted Fern - It is known for its unique and colorful foliage, and it can thrive in shady spots. " +
                "Search results from search words: " +
                "{" +
                "  plantsMap : {" +
                "    Japanese Painted Fern : [ {" +
                "      id : 1114," +
                "      description : The Japanese painted fern (Athyrium 'Branford Rambler') is an amazing plant species. Its cascading fronds emerge from crowns of vivid maroon and are deeply cut, resembling tiny butterflies. The silvery-gray fronds also have touches of purple and pink, depending on how much sunlight it receives. This fern does best in shade or part shade conditions, and its glossy foliage will add texture and a pop of color to any garden or living space. Its ability to tolerate drought and humidity makes it a great choice for anyone, and it can also be used in containers and as an understory plant. Its unique color, texture, and form make the Japanese painted fern a remarkable addition to any outdoor space.\",\n" +
                "      indoor : false," +
                "      common_name : Japanese painted fern," +
                "      scientific_name : [ Athyrium 'Branford Rambler' ]" +
                "    }, {" +
                "      id : 1128,\n" +
                "      \"description\" : \"The Japanese painted fern (Athyrium niponicum 'Crested Surf') is a stunning and low-maintenance plant. It is a deciduous fern that can survive in a wide range of temperatures, meaning it is perfect for gardens and patio gardens alike. The foliage is eye-catching with its crested, silver and green fronds that are peppered with rusty brown markings - the combination of colors adding up to the remarkable impact of the plant. It is known to be disease and pest free, making it easy to grow and maintain. Additionally, this variety of fern is easy to propagate from spores so you can enjoy it for many years to come.\",\n" +
                "      \"indoor\" : false,\n" +
                "      \"common_name\" : \"Japanese painted fern\",\n" +
                "      \"scientific_name\" : [ \"Athyrium niponicum 'Crested Surf'\" ]\n" +
                "    }\n" +
                "   ],\n" +
                "    \"Hosta\" : [ {\n" +
                "      \"id\" : 3840,\n" +
                "      \"description\" : \"Hosta 'Blonde Elf' is a hardy, deciduous perennial that grows in clumps of spiky, variegated foliage. This particular hosta has bright, chartreuse leaves with a light yellow center and dark green edges. It reaches around 10 inches in height and a spread of 12 inches, making it great for smaller spaces. It is a low maintenance plant and perfect for adding color and contrast to shady gardens and wooded areas. Great for pollinators, and deer resistant, 'Blonde Elf' blooms in the summer with lavender flowers. It is an easygoing plant and perfect for novice gardeners.\",\n" +
                "      \"indoor\" : false,\n" +
                "      \"common_name\" : \"hosta\",\n" +
                "      \"scientific_name\" : [ \"Hosta 'Blonde Elf'\" ]\n" +
                "    }, {\n" +
                "      \"id\" : 3834,\n" +
                "      \"description\" : \"Hosta 'Autumn Frost' is a popular, low-maintenance perennial with bright and attractive foliage. Its yellow-green leaves have a wide creamy-white margin with an irregular wavy pattern, providing a striking contrast of colors. Vigorous and hardy, this ground cover is ideal for both sunny and partially shaded areas. It also makes a great addition to borders and beds. Drought-tolerant and with attractive blooms in the summer, Hosta 'Autumn Frost' is a great choice for the garden.\",\n" +
                "      \"indoor\" : false,\n" +
                "      \"common_name\" : \"hosta\",\n" +
                "      \"scientific_name\" : [ \"Hosta 'Autumn Frost'\" ]\n" +
                "    }\n" +
                "   ]\n" +
                "  }\n" +
                "}\n" +
                "Return Japanese Painted Fern: 1128 and Hosta: 3840";
        FunctionExecutor functionExecutor = new FunctionExecutor(Collections.singletonList(ChatFunction.builder()
                .name("getPlantById")
                .description("Get plants by their id")
                .executor(PlantsIdMap.class, p -> {
                    Map<String, Plant> plantsMap = new HashMap<>();
                    for (Map.Entry<String, Integer> entry : p.plantsIdMap.entrySet()) {
                        System.out.println("Key: " + entry.getKey());
                        System.out.println("Value: " + entry.getValue());
                        plantsMap.put(entry.getKey(), plantService.getPlantById(entry.getValue()));
                    }
                    return new PlantMap(plantsMap);
                })
                .build()));
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemRoleMsg);
        messages.add(systemMessage);

        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), text);
        messages.add(firstMsg);

        return buildPlantMapFromExecutedFunctions(messages, functionExecutor, openAiService);
    }

    private JsonNode buildJsonFromExecutedFunctions(List<ChatMessage> messages, FunctionExecutor functionExecutor, OpenAiService service) {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(MODEL)
                .messages(messages)
                .functions(functionExecutor.getFunctions())
                .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("auto"))
                .n(1)
                .maxTokens(maxTokens)
                .logitBias(new HashMap<>())
                .build();
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();

        ChatFunctionCall functionCall = responseMessage.getFunctionCall();
        JsonNode results = null;
        if (functionCall != null) {
            System.out.println("Trying to execute " + functionCall.getName() + "...");
            results = functionExecutor.executeAndConvertToJson(functionCall);
            if (results != null) {
                System.out.println("Executed " + functionCall.getName() + ".");
            } else {
                System.err.println("Something went wrong with the execution of " + functionCall.getName() + "...");
            }
        }
        return results;
    }

    private PlantSearchAiResponse buildPSARFromExecutedFunctions(List<ChatMessage> messages, FunctionExecutor functionExecutor, OpenAiService service) {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(MODEL)
                .messages(messages)
                .functions(functionExecutor.getFunctions())
                .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("auto"))
                .n(1)
                .maxTokens(maxTokens)
                .logitBias(new HashMap<>())
                .build();
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();

        ChatFunctionCall functionCall = responseMessage.getFunctionCall();
        PlantSearchAiResponse results = null;
        if (functionCall != null) {
            System.out.println("Trying to execute " + functionCall.getName() + "...");
            results = functionExecutor.execute(functionCall);
            if (results != null) {
                System.out.println("Executed " + functionCall.getName() + ".");
            } else {
                System.err.println("Something went wrong with the execution of " + functionCall.getName() + "...");
            }
        }
        return results;
    }

    private PlantMap buildPlantMapFromExecutedFunctions(List<ChatMessage> messages, FunctionExecutor functionExecutor, OpenAiService service) {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(MODEL)
                .messages(messages)
                .functions(functionExecutor.getFunctions())
                .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("getPlantById"))
                .n(1)
                .maxTokens(maxTokens)
                .logitBias(new HashMap<>())
                .build();
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        System.out.println(responseMessage.getContent());
        ChatFunctionCall functionCall = responseMessage.getFunctionCall();
        System.out.println(responseMessage);
        PlantMap results = null;
        if (functionCall != null) {
            System.out.println("Trying to execute " + functionCall.getName() + "...");
            results = functionExecutor.execute(functionCall);
            if (results != null) {
                System.out.println("Executed " + functionCall.getName() + ".");
            } else {
                System.err.println("Something went wrong with the execution of " + functionCall.getName() + "...");
            }
        }
        return results;
    }

}
