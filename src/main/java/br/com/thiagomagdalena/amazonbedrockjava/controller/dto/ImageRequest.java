package br.com.thiagomagdalena.amazonbedrockjava.controller.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ImageRequest {

    private String prompt;
    private Long seed;
    private String stylePreset;
}
