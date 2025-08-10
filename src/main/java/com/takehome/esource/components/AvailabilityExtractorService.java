package com.takehome.esource.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AvailabilityExtractorService {

    public Integer largestPrime(Integer[] array) {
        Arrays.sort(array, Collections.reverseOrder());
        return Arrays.stream(array)
                .filter(AvailabilityExtractorService::isPrime)
                .findFirst()
                .orElse(-1);
    }

    public Integer[] availableNumbers(int[][] arrays, int start, int end) {
        var universe = universe(start, end);
        universe.removeAll(usedIntegers(arrays, start, end));
        return universe.toArray(Integer[]::new);
    }

    private Set<Integer> universe(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toSet());
    }

    private Set<Integer> usedIntegers(int[][] arrays, int start, int end) {
        return Arrays.stream(arrays)
                .flatMapToInt(Arrays::stream)
                .filter(n -> start <= n && n <= end)
                .distinct()
                .boxed()
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    // extrapolate into a static class
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
