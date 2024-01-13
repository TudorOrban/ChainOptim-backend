package org.chainoptim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChainOptimizerApplication {
	private static final Logger logger = LoggerFactory.getLogger(ChainOptimizerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChainOptimizerApplication.class, args);
	}

}
