package br.com.thiagomagdalena.amazonbedrockjava.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ImageGenerationConfigBase {

    private Integer width;
    private Integer height;
    private String quality;
    private Double cfgScale;
    private Long seed;
}
