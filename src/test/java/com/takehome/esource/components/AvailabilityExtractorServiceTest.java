package com.takehome.esource.components;

import com.takehome.esource.config.ArrayProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
public class AvailabilityExtractorServiceTest {

    @Autowired ArrayProperties arrayProperties;
    @Autowired ArrayGeneratorService arrayGeneratorService;
    @Autowired AvailabilityExtractorService service;

    @Test
    public void given_whenAvailableNumbers_then() {

        var array = service.availableNumbers(arrayGeneratorService.generateArrays(), 0, 50);

        System.out.println("Printing...."+ array.length);

        for (int i : array) {
            System.out.print(i+" ");
        }
        System.out.println();
    }

    @Test
    public void given_whenLargestPrime_then() {

        var array = service.availableNumbers(arrayGeneratorService.generateArrays(), 0, 50);

        System.out.println("Largest Prime.... "+ service.largestPrime(array));
    }

    @Test
    public void given_whenAvailableNumbersConcurrent_then() throws ExecutionException, InterruptedException {

        var array = service.availableNumbersConcurrent(arrayGeneratorService.generateArrays(), 0, 50);

        System.out.println("Printing...."+ array.length);

        for (int i : array) {
            System.out.print(i+" ");
        }
        System.out.println();
    }

}
