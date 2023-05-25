package com.multiplex.booking;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDateTime;

//final LocalDateTime interval;

//@Repository
interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByShowingTime(LocalDateTime showingTime);

    List<Showing> findByShowingTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    //@Query //(SELECT t from Showing t WHERE )
    //List<Showing> findByShowingTimeInterval(LocalDateTime showingTime@plusHours(1));
}
