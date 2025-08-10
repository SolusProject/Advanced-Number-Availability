package com.takehome.esource.components;

import com.takehome.esource.config.ArrayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ArrayGeneratorService {

    private final ArrayProperties arrayProperties;

    public int[][] generateArrays() {
        return getCountAsStream()
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

    public Stream<Integer> getCountAsStream() {
        return arrayProperties.getCount()
                .map(c -> IntStream.range(0, c))
                .map(IntStream::boxed)
                .orElse(Stream.empty());
    }

    public static int getRandomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
