package com.example.intern6;

import com.example.intern6.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {
    private final GeneratorService generatorService;

    @GetMapping("/generateFakeData")
    public List<Users> generateNext10Data(
            @RequestParam(name = "dataLength", defaultValue = "0") int dataLength,
            @RequestParam(name = "country", defaultValue = "ru") String country,
            @RequestParam(name = "errorCount", defaultValue = "0") double errorCount,
            @RequestParam(name = "seed", defaultValue = "0") int seed
    ) {
        if (dataLength != 0){
            return generatorService.generateNext10Data(dataLength, country, errorCount, seed);
        }
        return generatorService.generateUser(country, errorCount, seed);
    }
}
