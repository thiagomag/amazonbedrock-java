package br.com.thiagomagdalena.amazonbedrockjava.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StableDiffusionImageConfig extends ImageGenerationConfigBase {

        private List<StableDiffusionTextPrompt> textPrompt;
        private Integer steps;
        private String stylePreset;

        @Override
        public String toString() {
            String base = "\"width\":" + getWidth() + "," +
                    "\"height\":" + getHeight() + "," +
                    "\"cfg_scale\":" + getCfgScale() + "," +
                    "\"seed\":" + getSeed();
            if (stylePreset != null && !stylePreset.isEmpty()) {
                base += ",\"style_preset\":" + stylePreset + "\"";
            }
            return "{" +
                    "\"text_prompts\":" + textPrompt + "," +
                    "\"steps\":" + steps + "," +
                    base +
                    "}";
        }
}
