package dev.langchain4j.example;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;

import java.util.Collections;

import static dev.langchain4j.agent.tool.JsonSchemaProperty.*;
import static dev.langchain4j.example.PulledModelNameInOllama.*;

/**
 * WORK IN PROGRESS
 * Output:
 * Ollama does not support tools (low level) yet (2024-05-07) ???
 * Result: Exception in thread "main" java.lang.IllegalArgumentException: Tools are currently not supported by this model
 *
 * Tried with mistral:7b, codellama:7b, llama3:8b, mistral:7b-instruct
 *
 * sources:
 * - https://github.com/langchain4j/langchain4j-examples/blob/main/tutorials/src/main/java/_10_ServiceWithToolsExample.java
 * - https://docs.langchain4j.dev/tutorials/tools/
 * - https://github.com/langchain4j/langchain4j-examples/blob/main/other-examples/src/main/java/ServiceWithToolsExample.java
 * - https://stackoverflow.com/questions/78211016/langchain4j-how-to-create-a-service-aiservices-with-memory-and-streaming-for
 * - https://github.com/langchain4j/langchain4j/issues/612
 * - https://github.com/ollama/ollama/issues/2417
 */
public class OllamaChatWithToolsLowLevelExample {

    public static void main(String[] args) {
        System.out.println("Hello, OllamaChat!");

        String request = "What will the weather be like in London tomorrow?";

        System.out.println("Request: " + request);

        String modelName = MISTRAL_INSTRUCT.getModelName();
        String baseUrl = "http://localhost:11434";

        ChatLanguageModel model = OllamaChatModel.builder()
                .modelName(modelName)
                .baseUrl(baseUrl)
                .build();

        // Where can I add the code to use getWeather API?
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("getWeather")
                .description("Returns the weather forecast for a given city")
                .addParameter("city", type("string"), description("The city for which the weather forecast should be returned"))
                .addParameter("temperatureUnit", enums(TemperatureUnit.class))
                .build();

        UserMessage userMessage = UserMessage.from(request);

        Response<AiMessage> response = model.generate(Collections.singletonList(userMessage), toolSpecification);
        AiMessage aiMessage = response.content();

        System.out.println("Answer: ");
        System.out.println(aiMessage.text());

    }

}
