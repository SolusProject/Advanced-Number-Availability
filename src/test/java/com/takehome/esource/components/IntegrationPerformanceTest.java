package com.takehome.esource.components;

import com.takehome.esource.config.ArrayProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationPerformanceTest {

    @Autowired ArrayProperties properties;
    @Autowired ArrayGeneratorService generatorService;
    @Autowired AvailabilityExtractorService service;

    @Test
    void testPerformance() throws InterruptedException {
        var array = generatorService.generateArrays();
        var min = 1;
        var max = 1000;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }

        var start = System.nanoTime();
        service.getAvailableNumbers(array, min, max);
        var end = System.nanoTime();
        System.out.println("Execution time: "+(end-start));

        start = System.nanoTime();
        service.getAvailableNumbersConcurrent(array, min, max);
        end = System.nanoTime();
        System.out.println("Execution time concurrent: "+(end-start));
    }

}
