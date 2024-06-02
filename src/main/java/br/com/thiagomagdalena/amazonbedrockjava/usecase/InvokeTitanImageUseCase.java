package br.com.thiagomagdalena.amazonbedrockjava.usecase;

import br.com.thiagomagdalena.amazonbedrockjava.controller.dto.ImageRequest;
import br.com.thiagomagdalena.amazonbedrockjava.usecase.dto.TitanImageGenerationConfig;
import br.com.thiagomagdalena.amazonbedrockjava.usecase.dto.TitanImageRequest;
import br.com.thiagomagdalena.amazonbedrockjava.usecase.dto.TitanImageTextToImageParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.util.concurrent.ExecutionException;

@Service
public class InvokeTitanImageUseCase extends BedRockUseCaseBase {

    public static final String AMAZON_TITAN_IMAGE_GENERATOR_V_1 = "amazon.titan-image-generator-v1";

    /**
     * Invokes the Amazon Titan image generation model to create an image using the
     * input
     * provided in the request body.
     * <p>
     * prompt The prompt that you want Amazon Titan to use for image
     *               generation.
     * seed   The random noise seed for image generation (Range: 0 to
     *               2147483647).
     * @return A Base64-encoded string representing the generated image.
     */
    public String invokeTitanImage(ImageRequest imageRequest) throws JSONException {
        /*
         * The different model providers have individual request and response formats.
         * For the format, ranges, and default values for Titan Image models refer to:
         * https://docs.aws.amazon.com/bedrock/latest/userguide/model-parameters-titan-
         * image.html
         */

        final var client = buildBedrockRuntimeAsyncClient();

        final var textToImageParams = TitanImageTextToImageParams.builder()
                .text(imageRequest.getPrompt())
                .build();

        final var imageGenerationConfig = TitanImageGenerationConfig.builder()
                .numberOfImages(1)
                .build();
        imageGenerationConfig.setQuality("standard");
        imageGenerationConfig.setCfgScale(8.0);
        imageGenerationConfig.setHeight(512);
        imageGenerationConfig.setWidth(512);
        imageGenerationConfig.setSeed(imageRequest.getSeed());

        final var titanImageRequest = TitanImageRequest.builder()
                .textToImageParams(textToImageParams)
                .imageGenerationConfig(imageGenerationConfig)
                .build().toString();

        final var payload = new JSONObject(titanImageRequest);

        final var request = buildInvokeModelRequest(payload, AMAZON_TITAN_IMAGE_GENERATOR_V_1);
        final var completableFuture = invokeModel(client, request);

        String base64ImageData = "";
        try {
            InvokeModelResponse response = completableFuture.get();
            JSONObject responseBody = new JSONObject(response.body().asUtf8String());
            base64ImageData = responseBody
                    .getJSONArray("images")
                    .getString(0);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(e.getMessage());
        } catch (ExecutionException e) {
            System.err.println(e.getMessage());
        }

        return base64ImageData;
    }
}
