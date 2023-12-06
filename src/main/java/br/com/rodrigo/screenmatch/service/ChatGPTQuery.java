package br.com.rodrigo.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ChatGPTQuery {
    public static String getTranslation(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_API_KEY"));

        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}
