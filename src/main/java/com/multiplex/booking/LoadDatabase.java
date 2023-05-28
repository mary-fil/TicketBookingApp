package com.multiplex.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(ShowingRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Showing(LocalDateTime.of(2023, 5, 24, 20, 0), "b", 3)));
      log.info("Preloading " + repository.save(new Showing(LocalDateTime.of(2023, 5, 24, 18, 0), "a", 1)));
      log.info("Preloading " + repository.save(new Showing(LocalDateTime.of(2023, 5, 24, 14, 0), "a", 2)));
    };
  }
}