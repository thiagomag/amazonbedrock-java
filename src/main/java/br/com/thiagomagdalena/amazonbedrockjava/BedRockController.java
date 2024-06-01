package br.com.thiagomagdalena.amazonbedrockjava;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bedrock")
public class BedRockController {

    private final BedRockService bedRockService;

    @PostMapping
    public String invokeTitanImage(@RequestBody String prompt) {
        final var seed = (long) (Math.random() * Integer.MAX_VALUE);
        return bedRockService.invokeTitanImage(prompt, seed);
    }
}
