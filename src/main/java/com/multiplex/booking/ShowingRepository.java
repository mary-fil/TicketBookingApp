package com.multiplex.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByShowingTime(LocalDateTime showingTime);

    List<Showing> findByShowingTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    Showing findByMovieTitle(String movieTitle);

    Showing findByMovieTitleAndId(String movieTitle, Long roomId);
}
