package com.multiplex.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(ShowingRepository repository) {

    // creating an array of seats
    List<Integer> seats = new ArrayList<>();
    seats.add(1);
    seats.add(2);
    seats.add(3);
    seats.add(4);

    return args -> {
      log.info("Preloading " + repository.save(new Showing(LocalDateTime.of(2023, 5, 24, 18, 0), "movie1", 1, seats)));
      log.info("Preloading " + repository.save(new Showing(LocalDateTime.of(2023, 5, 24, 19, 0), "movie2", 2, seats)));
    };
  }
}