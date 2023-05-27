package com.multiplex.booking;

import org.springframework.data.jpa.repository.JpaRepository;
//mport org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDateTime;

//final LocalDateTime interval;

//@Repository
interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByShowingTime(LocalDateTime showingTime);

    List<Showing> findByShowingTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    Showing findByMovieTitle(String movieTitle);

    Showing findByMovieTitleAndId(String movieTitle, Long roomId);

    //Showing getReservation(String movieTitle, int roomId, int row_nr, int column_nr, String name, String surname);
}
