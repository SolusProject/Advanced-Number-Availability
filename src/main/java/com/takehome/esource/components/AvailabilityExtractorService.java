package com.takehome.esource.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class AvailabilityExtractorService {

    public Integer largestPrime(Integer[] array) {
        Arrays.sort(array, Collections.reverseOrder());
        return Arrays.stream(array)
                .filter(AvailabilityExtractorService::isPrime)
                .findFirst()
                .orElse(-1);
    }

    public Integer[] availableNumbersConcurrent(int[][] arrays, int start, int end) throws InterruptedException {
        var universe = universe(start, end);

        var executor = Executors.newFixedThreadPool(2*Runtime.getRuntime().availableProcessors()); // basic

        var tasks = Arrays.stream(arrays)
                .map(array -> (Callable<Set<Integer>>) () -> extractUsedNumbers(array))
                .collect(Collectors.toList());

        var usedNumbers = executor.invokeAll(tasks)
                .stream()
                .map(this::getFromFuture)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        executor.shutdown();

        universe.removeAll(usedNumbers);
        return universe.toArray(Integer[]::new);
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

    private Set<Integer> getFromFuture(Future<Set<Integer>> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error extracting used numbers", e);
            return Collections.emptySet();
        }
    }

    private Set<Integer> extractUsedNumbers(int[] array) {
        return Arrays.stream(array).boxed().collect(Collectors.toSet());
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
