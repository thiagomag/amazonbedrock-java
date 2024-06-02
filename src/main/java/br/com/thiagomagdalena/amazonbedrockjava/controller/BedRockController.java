package br.com.thiagomagdalena.amazonbedrockjava.controller;

import br.com.thiagomagdalena.amazonbedrockjava.controller.dto.ImageRequest;
import br.com.thiagomagdalena.amazonbedrockjava.usecase.InvokeStableDiffusionUseCase;
import br.com.thiagomagdalena.amazonbedrockjava.usecase.InvokeTitanImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bedrock")
public class BedRockController {

    private final InvokeTitanImageUseCase invokeTitanImageUseCase;
    private final InvokeStableDiffusionUseCase invokeStableDiffusionUseCase;

    @PostMapping(path = "/titan/image")
    public String invokeTitanImage(@RequestBody ImageRequest imageRequest) {
        final var seed = (long) (Math.random() * Integer.MAX_VALUE);
        imageRequest.setSeed(seed);
        return invokeTitanImageUseCase.invokeTitanImage(imageRequest);
    }

    @PostMapping(path = "/stable_difusion/image")
    public String invokeStableDiffusionImage(@RequestBody ImageRequest imageRequest) {
        final var seed = (long) (Math.random() * Integer.MAX_VALUE);
        imageRequest.setSeed(seed);
        return invokeStableDiffusionUseCase.invokeStableDiffusion(imageRequest);
    }
}
