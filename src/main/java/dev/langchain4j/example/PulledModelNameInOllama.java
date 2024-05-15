package dev.langchain4j.example;

public enum PulledModelNameInOllama {

    CODELLAMA("codellama:7b"),
    MISTRAL("mistral:7b"),
    MISTRAL_INSTRUCT("mistral:7b-instruct"),
    LLAMA3("llama3:8b");

    private final String modelName;

    PulledModelNameInOllama(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

}
