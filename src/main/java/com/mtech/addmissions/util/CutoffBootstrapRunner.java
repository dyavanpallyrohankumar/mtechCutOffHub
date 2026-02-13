package com.mtech.addmissions.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CutoffBootstrapRunner implements CommandLineRunner {
    // private final CutoffServiceImp cutoffService;

    @Override
    public void run(String... args) {
        // cutoffService.bootstrapAllCutoffs();
    }
}
