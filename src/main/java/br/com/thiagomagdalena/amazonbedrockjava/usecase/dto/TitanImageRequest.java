package br.com.thiagomagdalena.amazonbedrockjava.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TitanImageRequest {

    @Builder.Default
    private String taskType = "TEXT_IMAGE";
    private TitanImageTextToImageParams textToImageParams;
    private TitanImageGenerationConfig imageGenerationConfig;

    @Override
    public String toString() {
        return "{" +
                "\"taskType\":\"" + taskType + "\"," +
                "\"textToImageParams\":" + textToImageParams + "," +
                "\"imageGenerationConfig\":" + imageGenerationConfig +
                "}";
    }
}