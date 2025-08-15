package com.takehome.esource.config;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Valid
@Slf4j
@Data
@Validated
@Component
@ConfigurationProperties(prefix = "esource.array")
public class ArrayProperties {

    @Min(value = 1, message = "Size must be greater than 0")
    @Max(value = 1000000, message = "Size must be less than or equal to 1,000,000")
    private Integer count;

    @Min(value = 1, message = "Size must be greater than 0")
    @Max(value = 1000000, message = "Size must be less than or equal to 1,000,000")
    private Integer size; // size is the same for every array

    // add value constraints
    private Integer min;
    // add value constraints
    private Integer max;

    @PostConstruct
    public void validateUniverse() {
        if(min >= max) {
            throw new IllegalArgumentException("Min should be greater than Max");
        }

        if(size - 1 > max - min) {
            throw new IllegalArgumentException("Invalid Array Configuration Universe");
        }
    }
}
