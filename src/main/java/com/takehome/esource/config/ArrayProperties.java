package com.takehome.esource.config;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "esource.array")
public class ArrayProperties {

    @Min(value = 1, message = "Size must be greater than 0")
    @Max(value = 1000000, message = "Size must be less than or equal to 1,000,000")
    private Optional<Integer> count = Optional.empty();

    @Min(value = 1, message = "Size must be greater than 0")
    @Max(value = 1000000, message = "Size must be less than or equal to 1,000,000")
    private Optional<Integer> size = Optional.empty(); // size is the same for every array

    // add value constraints
    private int min;
    // add value constraints
    private int max;

    public Stream<Integer> getCountAsStream() {
        return count.map(c -> IntStream.range(0, c))
                .map(IntStream::boxed)
                .orElse(Stream.empty());
    }

    @PostConstruct
    public void validateUniverse() {
        if(min >= max) {
            throw new IllegalArgumentException("Min should be greater than Max");
        }

        if(size.orElse(0) - 1 > max - min) {
            throw new IllegalArgumentException("Invalid Array Configuration Universe");
        }
    }
}
