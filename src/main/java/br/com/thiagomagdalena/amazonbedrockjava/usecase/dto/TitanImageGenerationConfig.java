package br.com.thiagomagdalena.amazonbedrockjava.usecase.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TitanImageGenerationConfig extends ImageGenerationConfigBase {

    private Integer numberOfImages;

    @Override
    public String toString() {
        return "{" +
                "\"numberOfImages\":" + numberOfImages + "," +
                "\"width\":" + getWidth() + "," +
                "\"height\":" + getHeight() + "," +
                "\"quality\":\"" + getQuality() + "\"," +
                "\"cfgScale\":" + getCfgScale() + "," +
                "\"seed\":" + getSeed() +
                "}";
    }
}
