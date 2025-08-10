package com.takehome.esource.components;

import com.takehome.esource.config.ArrayProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArrayGeneratorServiceTest {

    @Autowired ArrayProperties arrayProperties;
    @Autowired ArrayGeneratorService service;

    @Test
    public void givenArrayProperties_whenGenerateArrays_thenGenerateArrays() {

        var array = service.generateArrays();

        for (int[] i : array) {
            for (int j : i) {
                System.out.print(j+" ");
            }
            System.out.println();
        }
    }



}
