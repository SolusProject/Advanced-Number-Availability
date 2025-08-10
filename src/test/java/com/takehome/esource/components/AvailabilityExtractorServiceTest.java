package com.takehome.esource.components;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.Future;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AvailabilityExtractorServiceTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AvailabilityExtractorServiceTest.class);

    @InjectMocks AvailabilityExtractorService service;

    private static Stream<Arguments> providePrimeArguments() {
        return Stream.of(
                Arguments.of(new Integer[] {1}, -1),
                Arguments.of(new Integer[] {2}, 2),
                Arguments.of(new Integer[] {3, 5}, 5),
                Arguments.of(new Integer[] {4}, -1),
                Arguments.of(new Integer[] {25}, -1)
        );
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        new int[][] {
                                {1, 2, 3},
                                {4, 5, 6},
                                {7, 8, 9}
                        },
                        1, 10,
                        new Integer[] {10}
                ),
                Arguments.of(
                        new int[][] {
                                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
                        },
                        3, 8,
                        new Integer[] {}
                ),
                Arguments.of(
                        new int[][] {},
                        1, 10,
                        new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
                ),
                Arguments.of(
                        new int[][] {
                                {1, 2, 3}
                        },
                        10, 20,
                        new Integer[] {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    public void givenArraysAndBoundaries_whenAvailableNumbers_thenAvailable(int[][] array, int min, int max, Integer[] expected) {

        var actual = service.getAvailableNumbers(array, min, max);

        assertArrayEquals(expected, actual, "The arrays should be equal");

    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    public void givenArraysAndBoundaries_whenAvailableNumbersConcurrent_thenAvailable(int[][] array, int min, int max, Integer[] expected) throws InterruptedException {

        var actual = service.getAvailableNumbersConcurrent(array, min, max);

        assertArrayEquals(expected, actual, "The arrays should be equal");
    }

    @Test
    void testGetFromFuture_withInterruptedException() throws InterruptedException, ExecutionException {
        var mockFuture = mock(Future.class);
        when(mockFuture.get()).thenThrow(InterruptedException.class);
        var result = service.getFromFuture(mockFuture);
        assertEquals(Collections.emptySet(), result, "Expected empty set when InterruptedException is caught");
    }

    @ParameterizedTest
    @MethodSource("providePrimeArguments")
    void foo(Integer[] input, Integer expected) {
        assertEquals(expected, service.getLargestPrime(input), "Primes should match");
    }

}
