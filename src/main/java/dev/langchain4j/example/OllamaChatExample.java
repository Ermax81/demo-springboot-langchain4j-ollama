package dev.langchain4j.example;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.time.Duration;

import static dev.langchain4j.example.PulledModelNameInOllama.*;

public class OllamaChatExample {

    public static void main(String[] args) {
        System.out.println("Hello, OllamaChat!");

        String request = "Provide 3 short bullet points explaining why Java is awesome";

        System.out.println("Request: " + request);

        // The model name to use (e.g., "orca-mini", "mistral", "llama2", "codellama", "phi", or
        // "tinyllama", "llama3")
        String modelName = CODELLAMA.getModelName();
        String baseUrl = "http://localhost:11434";

        // Build the ChatLanguageModel https://docs.langchain4j.dev/integrations/language-models/ollama/
        ChatLanguageModel model = OllamaChatModel.builder()
            .modelName(modelName)
            .baseUrl(baseUrl)
            //.temperature(0.5)
            .timeout(Duration.ofSeconds(180L)) // wait for 180 seconds, can help if the cpu is slow and gpu is not used
            .build();

        // Example usage
        String answer = model.generate(request);
        System.out.println("Answer: ");
        System.out.println(answer);

        /**
         * Output example:
         * * **Object-oriented programming**: Java is a powerful and expressive language that supports object-oriented programming (OOP) concepts, such as encapsulation, inheritance, and polymorphism. This makes it easy to write reusable, modular code that can be easily maintained and scaled.
         * * **Platform independence**: Java is a platform-independent language, which means that the same code can run on any device that has a Java Virtual Machine (JVM) installed. This makes it easy to deploy Java applications across different platforms without having to worry about compatibility issues.
         * * **Growing community and ecosystem**: Java has a large and active developer community, with many libraries, frameworks, and tools available to help developers build and deploy their applications efficiently. The Java community is also very supportive of newcomers and beginners, making it an ideal language for those just starting out in software development.
         */
    }
}
