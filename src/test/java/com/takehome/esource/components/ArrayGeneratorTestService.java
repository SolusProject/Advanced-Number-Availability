package com.takehome.esource.components;


import com.takehome.esource.config.ArrayProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ArrayGeneratorTestService {

    @Autowired ArrayProperties arrayProperties;
    @Autowired ArrayGeneratorService service;

    @Test
    public void given_when_then() {

        var array = service.generateArrays();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");  // Print each element followed by a space
            }
            System.out.println();  // Move to the next line after printing one row
        }
    }

}
