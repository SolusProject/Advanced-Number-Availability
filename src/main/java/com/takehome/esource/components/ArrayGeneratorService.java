package com.takehome.esource.components;

import com.takehome.esource.config.ArrayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;


@Slf4j
@Service
@RequiredArgsConstructor
public class ArrayGeneratorService {

    private final ArrayProperties arrayProperties;

    private static final Random random = new Random();

    public int[][] generateArrays() {
        return arrayProperties.getCountAsStream()
                .map(i -> generateArray())// can be extended
                .toArray(int[][]::new);
    }

    private int [] generateArray() {
        return Stream.generate(withSupplier())
                .distinct()
                .limit(arrayProperties.getSize().orElse(0))
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private Supplier<Integer> withSupplier() {
        return () -> getRandomBetween(arrayProperties.getMin(), arrayProperties.getMax());
    }

    public static int getRandomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
