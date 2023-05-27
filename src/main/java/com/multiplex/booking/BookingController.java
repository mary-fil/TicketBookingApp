package com.multiplex.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Sort;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BookingController {
    private final ShowingRepository showings;
    final int hour = 1;

    BookingController(ShowingRepository showings) {
        this.showings = showings;
    }

    // Aggregate root
    @GetMapping("/showings")
    List<Showing> all() {
        Sort sorted = Sort.by(Sort.Direction.ASC, "movieTitle", "showingTime");
        return showings.findAll(sorted);
    }

    @PostMapping("/showings")
    Showing newShowing(@RequestBody Showing newShowing) {
        return showings.save(newShowing);
    }

    // Single item

    @GetMapping("/showingsById/{id}")
    Showing one(@PathVariable Long id) {

        return showings.findById(id).orElseThrow(() -> new ShowingNotFoundException(id));
    }

    @PutMapping("/showingsById/{id}")
    Showing replaceShowing(@RequestBody Showing newShowing, @PathVariable Long id) {

        return showings.findById(id)
                .map(showing -> {
                    showing.setShowingTime(newShowing.getShowingTime());
                    showing.setMovieTitle(newShowing.getMovieTitle());
                    showing.setRoomNr(newShowing.getRoomNr());
                    return showings.save(showing);
                })
                .orElseGet(() -> {
                    newShowing.setId(id);
                    return showings.save(newShowing);
                });
    }

    @DeleteMapping("/showingsById/{id}")
    void deleteShowing(@PathVariable Long id) {
        showings.deleteById(id);
    }

    // user selects the day and the time when he/she would like to see a movie
    // change the format of date?

    @GetMapping("/showingsByTime/{dateTime}")
    public List<Showing> getShowingByTime(@PathVariable("dateTime") LocalDateTime dateTime) {
        return showings.findByShowingTime(dateTime);
    }

    // system lists movies available in the given time interval - everything
    // variable hour -> chosen time interval
    @GetMapping("/showingsByInterval/{dateTime}")
    public List<Showing> getShowingsWithinTimeRange(@PathVariable("dateTime") LocalDateTime dateTime) {
        LocalDateTime startTime = dateTime.minusHours(hour);
        LocalDateTime endTime = dateTime.plusHours(hour);
        return showings.findByShowingTimeBetween(startTime, endTime);
    }

    // user chooses a particular screening
    // what if there are two screenings with the same title?
    // then -> choose by id?

    @GetMapping("/showingsByTitle/{movieTitle}")
    Showing one(@PathVariable("movieTitle") String movieTitle) {

        return showings.findByMovieTitle(movieTitle);
        // .orElseThrow(() -> new ShowingNotFoundException(movieTitle));
    }

    @GetMapping("/showingsByTitleAndId/{movieTitle}/{roomId}")
    Showing getByMovieTitleAndId(@PathVariable("movieTitle") String movieTitle, @PathVariable("roomId") Long roomId) {
        return showings.findByMovieTitleAndId(movieTitle, roomId);
        // .orElseThrow(() -> new ShowingNotFoundException(movieTitle, id));
    }

    // user chooses seats, and gives the name of the person doing the reservation
    // (name and surname)

    @GetMapping("/showings/{movieTitle}/{roomId}/{row_nr}/{column_nr}/{name}/{surname}")
    public Showing getReservation(
        @PathVariable("movieTitle") String movieTitle, 
        @PathVariable("roomId") Long roomId,
        @PathVariable("row_nr") int row_nr,
        @PathVariable("column_nr") int column_nr,
        @PathVariable("name") String name,
        @PathVariable("surname") String surname) {

        // Retrieve showing based on movie title and room ID
        Showing showing = showings.findByMovieTitleAndId(movieTitle, roomId);
        
        // Retrieve the list of seats in the showing
        Set<Seat> seats = showing.getSeats();
        
         // Find the seat with the specified row and column
        Optional<Seat> seatOptional = seats.stream()
        .filter(seat -> seat.getRow_nr() == row_nr && seat.getColumn_nr() == column_nr)
        .findFirst();

        if (seatOptional.isPresent()) {
            Seat seat = seatOptional.get();

            // Update the seat's information
            seat.setReserved(true);
            // TO DO
            seat.setName(name);
            seat.setSurname(surname);
        } else {
            // Handle the case when the seat is not found
            // TO DO
            //throw new SeatNotFoundException(row_nr, column_nr);
        }

        // Save the updated showing
        showings.save(showing);
        
        return showing;
    }
}