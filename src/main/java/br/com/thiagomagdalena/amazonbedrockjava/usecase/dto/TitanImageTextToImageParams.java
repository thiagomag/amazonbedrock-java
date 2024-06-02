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
public class TitanImageTextToImageParams {

    private String text;

    @Override
    public String toString() {
        return "{" +
                "\"text\":\"" + text + "\"" +
                "}";
    }
}
