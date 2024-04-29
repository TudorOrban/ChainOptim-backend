package org.chainoptim;

import org.chainoptim.core.user.jwt.JwtTokenProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class ChainOptimizerApplication {

	private static final Logger logger = Logger.getLogger(ChainOptimizerApplication.class.getName());

	public static void main(String[] args) {
		logger.info("Starting Chain Optimizer Application");
		SpringApplication.run(ChainOptimizerApplication.class, args);
	}
}
