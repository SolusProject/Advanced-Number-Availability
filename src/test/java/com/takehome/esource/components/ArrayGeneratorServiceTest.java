package com.takehome.esource.components;

import com.takehome.esource.config.ArrayProperties;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArrayGeneratorServiceTest {

    @Mock ArrayProperties arrayProperties;
    @InjectMocks ArrayGeneratorService service;

    @ParameterizedTest
    @CsvSource({
            "5, 10, 1, 100",
            "3, 5, 0, 50",
            "10, 10, 1, 100",
            "5, 15, 10, 30",
            "0, 15, 10, 30",
            "10, 0, 10, 30",
    })
    public void givenArrayProperties_whenGenerateArrays_thenGenerateArrays(int count, int size, int min, int max) {
        when(arrayProperties.getCount()).thenReturn(Optional.of(count));

        if(count > 0) {
            when(arrayProperties.getSize()).thenReturn(Optional.of(size));
            if(size > 0) {
                when(arrayProperties.getMin()).thenReturn(min);
                when(arrayProperties.getMax()).thenReturn(max);
            }
        }

        var arrays = service.generateArrays();

        assertEquals(count, arrays.length, "Number of arrays should match the count");

        for (int[] array : arrays) {
            assertEquals(size, array.length, "Each array should have the correct size");
        }

        for (int[] array : arrays) {
            for (int num : array) {
                assertTrue(num >= min && num <= max, "Generated number should be within the defined range");
            }
        }

        if(count == 0) {
            assertEquals(0, arrays.length);
        }

        if(count > 0) {
            // check duplicates
            var first = Arrays.stream(arrays[0])
                    .boxed()
                    .collect(Collectors.toSet());
            assertEquals(arrays[0].length, first.size(), "Array should have no duplicates");
        }
    }
}
