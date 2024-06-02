package br.com.thiagomagdalena.amazonbedrockjava.usecase;

import org.json.JSONObject;
import org.springframework.ai.bedrock.titan.api.TitanChatBedrockApi;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

import java.util.List;

@Service
public class InvokeTitanTextUseCase extends BedRockUseCaseBase {

    public static final String AMAZON_TITAN_TEXT_PREMIER_V_1_0 = "amazon.titan-text-premier-v1:0";

    /**
     * Invoke Titan Text with a system prompt and additional inference parameters,
     * using Titan's native request/response structure.
     *
     * @param userPrompt   - The text prompt to send to the model.
     * @param systemPrompt - A system prompt to provide additional context and instructions.
     * @return The {@link JSONObject} representing the model's response.
     */
    public JSONObject invokeWithSystemPrompt(String userPrompt, String systemPrompt) {

        // Create a Bedrock Runtime client in the AWS Region of your choice.
        var client = BedrockRuntimeClient.builder()
                .region(Region.US_EAST_1)
                .build();

        /* Assemble the input text.
         * For best results, use the following input text format:
         *     {{ system instruction }}
         *     User: {{ user input }}
         *     Bot:
         */
        var inputText = """
                %s
                User: %s
                Bot:\s
                """.formatted(systemPrompt, userPrompt);

        // Format the request payload using the model's native structure.
        final var nativeRequest = new TitanChatBedrockApi.TitanChatRequest(inputText,
                new TitanChatBedrockApi.TitanChatRequest.TextGenerationConfig(0.7F, 0.9F, 512, List.of()));

        // Encode and send the request.
        var response = client.invokeModel(request -> {
            request.body(SdkBytes.fromUtf8String(nativeRequest.toString()));
            request.modelId(AMAZON_TITAN_TEXT_PREMIER_V_1_0);
        });

        // Decode the native response body.
        var nativeResponse = new JSONObject(response.body().asUtf8String());

        // Extract and print the response text.
        var responseText = nativeResponse
                .getJSONArray("results")
                .getJSONObject(0)
                .getString("outputText");
        System.out.println(responseText);

        // Return the model's native response.
        return nativeResponse;
    }
}
