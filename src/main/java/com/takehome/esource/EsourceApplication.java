package com.takehome.esource;

import com.takehome.esource.components.ArrayGeneratorService;
import com.takehome.esource.components.AvailabilityExtractorService;
import com.takehome.esource.config.ArrayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class EsourceApplication implements CommandLineRunner {

	private final ArrayProperties arrayProperties;
	private final ArrayGeneratorService generatorService;
	private final AvailabilityExtractorService service;

	public static void main(String[] args) {
		SpringApplication.run(EsourceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("");
		log.info("NUM_ARRAYS: {}", arrayProperties.getCount());
		log.info("ARRAY_SIZE: {}", arrayProperties.getSize());
		log.info("MIN_RANGE: {}", arrayProperties.getMin());
		log.info("MAX_RANGE: {}", arrayProperties.getMax());

		var array = generatorService.generateArrays();
		var availableNumbers = service.getAvailableNumbersConcurrent(array, 10, 70);

		log.info("");

        for (int[] ints : array) {
            log.info("{}", ints);
        }

		log.info("");
		log.info("Available Numbers: {}", (Object) availableNumbers);
		log.info("Largest Prime {}", service.getLargestPrime(availableNumbers));
	}
}
