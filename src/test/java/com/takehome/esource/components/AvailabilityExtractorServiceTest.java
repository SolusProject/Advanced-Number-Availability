package com.takehome.esource.components;

import com.takehome.esource.config.ArrayProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AvailabilityExtractorServiceTest {

    @Autowired ArrayProperties arrayProperties;
    @Autowired ArrayGeneratorService arrayGeneratorService;
    @Autowired AvailabilityExtractorService service;

    @Test
    public void given_whenExecute_then() {

        var array = service.availableNumbers(arrayGeneratorService.generateArrays(), 0, 50);

        System.out.println("Printing...."+ array.length);

        for (int i : array) {
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
