package br.com.thiagomagdalena.amazonbedrockjava.usecase;


import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public abstract class BedRockUseCaseBase {

    protected static BedrockRuntimeAsyncClient buildBedrockRuntimeAsyncClient() {
        return BedrockRuntimeAsyncClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    protected static InvokeModelRequest buildInvokeModelRequest(JSONObject payload, String modelId) {
        return InvokeModelRequest.builder()
                .body(SdkBytes.fromUtf8String(payload.toString()))
                .modelId(modelId)
                .contentType("application/json")
                .accept("application/json")
                .build();
    }

    protected static CompletableFuture<InvokeModelResponse> invokeModel(BedrockRuntimeAsyncClient client, InvokeModelRequest request) {
        return client.invokeModel(request)
                .whenComplete((response, exception) -> {
                    if (exception != null) {
                        System.out.println("Model invocation failed: " + exception);
                    }
                });
    }

}
