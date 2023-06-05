// This class was previously used to load database.
// Leaving here if needed in future.

/*
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

  Room room1 = new Room(1,1,5);
  Room room2 = new Room(2,2,10);
  Room room3 = new Room(3,2,10);

  @Bean
  CommandLineRunner initDatabaseRoom(RoomRepository repository) {
    
    return args -> {
      //log.info("Preloading " + repository.save(room1));
      //log.info("Preloading " + repository.save(room2));
      //log.info("Preloading " + repository.save(room3));
    };
  }

  @Bean
  CommandLineRunner initDatabaseShowing(ShowingRepository repository) {

    return args -> {
      //log.info("Preloading " + repository.save(new Showing(LocalDateTime.of(2023, 7, 24, 20, 0), "b", room1)));
      //log.info("Preloading " + repository.save(new Showing(LocalDateTime.of(2023, 6, 24, 18, 0), "a", room2)));
      //log.info("Preloading " + repository.save(new Showing(LocalDateTime.of(2023, 5, 31, 21, 0), "a", room3)));
    };
  }

}

 */