package dev.langchain4j.example;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.output.Response;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static dev.langchain4j.example.PulledModelNameInOllama.*;

public class OllamaStreamingChatExample {

    public static void main(String[] args) {
        System.out.println("Hello, OllamaChat!");

        String request = "Provide 3 short bullet points explaining why Java is awesome";

        System.out.println("Request: " + request);

        String modelName = MISTRAL.getModelName();
        String baseUrl = "http://localhost:11434";

        StreamingChatLanguageModel model = OllamaStreamingChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .temperature(0.0)
                .timeout(Duration.ofSeconds(180L)) //default 60L
                .build();

        CompletableFuture<Response<AiMessage>> futureResponse = new CompletableFuture<>();
        model.generate(request, new StreamingResponseHandler<AiMessage>() {

            @Override
            public void onNext(String token) {
                System.out.print(token);
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                futureResponse.complete(response);
            }

            @Override
            public void onError(Throwable error) {
                futureResponse.completeExceptionally(error);
            }
        });

        futureResponse.join();

        /**
         * Output example (will appear in the console as the response is streamed):
         *  1. **Versatile and Platform Independent:** Java is a versatile programming language that can be used to build various applications, from desktop apps to mobile apps and web services. It's also platform independent, meaning Java code can run on any device that has a Java Virtual Machine (JVM) installed, making it an ideal choice for developing cross-platform applications.
         *
         * 2. **Large Community and Extensive Libraries:** Java has a large and active community of developers, which means there are plenty of resources available to help you learn and use the language effectively. Additionally, Java comes with an extensive set of libraries, such as JavaFX for creating user interfaces, Java EE for building enterprise applications, and Java SE for developing desktop apps.
         *
         * 3. **Strongly Typed and Secure:** Java is a strongly typed language, which means that the data type of every variable must be explicitly defined at compile time. This helps prevent common programming errors, such as using the wrong data type or forgetting to initialize a variable. Java also has built-in security features, like access control mechanisms and sandboxing, making it an excellent choice for developing secure applications.
         */
    }

}
