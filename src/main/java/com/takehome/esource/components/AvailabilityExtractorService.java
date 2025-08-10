package com.takehome.esource.components;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AvailabilityExtractorService {

    public int[] availableNumbers(int[][] arrays, int start, int end) {
        var universe = universe(start, end);
        universe.removeAll(usedIntegers(arrays, start, end));
        return universe.stream().mapToInt(Integer::intValue).toArray();
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
}
