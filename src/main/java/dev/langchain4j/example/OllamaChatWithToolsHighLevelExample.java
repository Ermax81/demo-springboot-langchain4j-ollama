package dev.langchain4j.example;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import static dev.langchain4j.example.PulledModelNameInOllama.*;

/**
 * WORK IN PROGRESS
 * Output:
 *  Ollama does not support tools (high level) yet (2024-05-07)
 *  Result: Exception in thread "main" java.lang.IllegalArgumentException: Tools are currently not supported by this model
 *
 * Tried with mistral:7b, codellama:7b, llama3:8b, mistral:7b-instruct
 *
 * Sources:
 * - https://github.com/langchain4j/langchain4j-examples/blob/main/tutorials/src/main/java/_10_ServiceWithToolsExample.java
 * - https://stackoverflow.com/questions/78211016/langchain4j-how-to-create-a-service-aiservices-with-memory-and-streaming-for
 * - https://github.com/langchain4j/langchain4j-examples/blob/main/other-examples/src/main/java/ServiceWithToolsExample.java
 * - https://github.com/langchain4j/langchain4j/issues/612
 * - https://github.com/ollama/ollama/issues/2417
 */
public class OllamaChatWithToolsHighLevelExample {

    static class Calculator {

        @Tool("Calculates the length of a string")
        int stringLength(String s) {
            System.out.println("Called stringLength() with s='" + s + "'");
            return s.length();
        }

        @Tool("Calculates the sum of two numbers")
        int add(int a, int b) {
            System.out.println("Called add() with a=" + a + ", b=" + b);
            return a + b;
        }

        @Tool("Calculates the square root of a number")
        double sqrt(int x) {
            System.out.println("Called sqrt() with x=" + x);
            return Math.sqrt(x);
        }
    }

    interface Assistant {
        String chat(String userMessage);
    }

    public static void main(String[] args) {
        System.out.println("Hello, OllamaChat!");

        String request = "Explaining why Java is awesome in less than 30 words and then calculate the sum of the length of all words in the sentence.";

        System.out.println("Request: " + request);

        String modelName = LLAMA3.getModelName();
        String baseUrl = "http://localhost:11434";

        ChatLanguageModel model = OllamaChatModel.builder()
                .modelName(modelName)
                .baseUrl(baseUrl)
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .tools(new Calculator())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();

        String answer = assistant.chat(request);
        System.out.println("Answer: ");
        System.out.println(answer);

    }

}
