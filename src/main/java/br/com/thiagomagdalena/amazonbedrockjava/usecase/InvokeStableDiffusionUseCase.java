package br.com.thiagomagdalena.amazonbedrockjava.usecase;

import br.com.thiagomagdalena.amazonbedrockjava.controller.dto.ImageRequest;
import br.com.thiagomagdalena.amazonbedrockjava.usecase.dto.StableDiffusionImageConfig;
import br.com.thiagomagdalena.amazonbedrockjava.usecase.dto.StableDiffusionTextPrompt;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class InvokeStableDiffusionUseCase extends BedRockUseCaseBase {

    public static final String STABILITY_STABLE_DIFFUSION_XL_V_1 = "stability.stable-diffusion-xl-v1";

    /**
     * Asynchronously invokes the Stability.ai Stable Diffusion XL model to create
     * an image based on the provided input.
     * <p>
     * prompt      The prompt that guides the Stable Diffusion model.
     * seed        The random noise seed for image generation (use 0 or omit
     *                    for a random seed).
     * stylePreset The style preset to guide the image model towards a
     *                    specific style.
     * @return A Base64-encoded string representing the generated image.
     */
    public String invokeStableDiffusion(ImageRequest imageRequest) throws JSONException {
        /*
         * The different model providers have individual request and response formats.
         * For the format, ranges, and available style_presets of Stable Diffusion
         * models refer to:
         * https://docs.aws.amazon.com/bedrock/latest/userguide/model-parameters-stability-diffusion.html
         */

        BedrockRuntimeAsyncClient client = buildBedrockRuntimeAsyncClient();

        final var wrappedPrompt = StableDiffusionTextPrompt.builder()
                .text(imageRequest.getPrompt())
                .weight(1)
                .build();
        final var payload = StableDiffusionImageConfig.builder()
                .textPrompt(List.of(wrappedPrompt))
                .steps(50)
                .build();

        payload.setCfgScale(10.0);
        payload.setWidth(1024);
        payload.setHeight(1024);
        payload.setQuality("standard");
        payload.setSeed(imageRequest.getSeed());

        final var stylePreset = imageRequest.getStylePreset();
        if (stylePreset != null && !stylePreset.isEmpty()) {
            payload.setStylePreset(stylePreset);
        }

        final var stringPayload = payload.toString();

        final var request = buildInvokeModelRequest(new JSONObject(stringPayload), STABILITY_STABLE_DIFFUSION_XL_V_1);
        final var completableFuture = invokeModel(client, request);

        String base64ImageData = "";
        try {
            InvokeModelResponse response = completableFuture.get();
            JSONObject responseBody = new JSONObject(response.body().asUtf8String());
            base64ImageData = responseBody
                    .getJSONArray("artifacts")
                    .getJSONObject(0)
                    .getString("base64");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(e.getMessage());
        } catch (ExecutionException e) {
            System.err.println(e.getMessage());
        }

        return base64ImageData;
    }
}
