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

    @PutMapping("/showingsById/{id}")
    Showing replaceShowing(@RequestBody Showing newShowing, @PathVariable Long id) {

        return showings.findById(id)
                .map(showing -> {
                    showing.setShowingTime(newShowing.getShowingTime());
                    showing.setMovieTitle(newShowing.getMovieTitle());
                    showing.setRoom(newShowing.getRoom());
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

    @GetMapping("/showingsById/{id}")
    Showing one(@PathVariable Long id) {

        return showings.findById(id).orElseThrow(() -> new ShowingNotFoundException(id));
    }

    // user chooses seats, and gives the name of the person doing the reservation
    // (name and surname)
    // TO DO
    // how to handle total amount? another class user?

    @PutMapping("/ReservationOfSeats")
    Showing replaceSeat(@RequestBody List<Seat> newSeats) {

        Showing showing = showings.findById(newSeats.get(0).getShowingId())
        .orElseThrow(() -> new ShowingNotFoundException(newSeats.get(0).getShowingId()));

        Set<Seat> seats = showing.getSeats();

        for (Seat newSeat : newSeats) {
            Optional<Seat> seatOptional = seats.stream()
                .filter(seat -> seat.getId().equals(newSeat.getId()))
                .findFirst();

            if (seatOptional.isPresent()) {
                Seat seat = seatOptional.get();
                seat.setReserved(true);
                seat.setName(newSeat.getName());
                seat.setSurname(newSeat.getSurname());
            } else {
                //throw new SeatNotFoundException(newSeat.getId());
            }
        }

        return showings.save(showing);
    }

    @PutMapping("/ReservationBySeatId/{id}")
    Showing replaceSeat(@RequestBody Seat newSeat, @PathVariable Long id) {

        return showings.findById(newSeat.getShowingId())
            .map(showing -> {
                // Retrieve the set of seats in the showing
                Set<Seat> seats = showing.getSeats();

                // Find the seat with the specified ID
                Optional<Seat> seatOptional = seats.stream()
                    .filter(seat -> seat.getId().equals(id))
                    .findFirst();

                if (seatOptional.isPresent()) {
                    Seat seat = seatOptional.get();
                    // Update the seat properties
                    seat.setReserved(true);
                    seat.setName(newSeat.getName());
                    seat.setSurname(newSeat.getSurname());
                    seat.setTicketType(newSeat.getTicketType());
                } else {
                    // Handle the case when the seat is not found
                    //throw new SeatNotFoundException(id);
                }

                return showings.save(showing);
            })
            .orElseThrow(() -> new ShowingNotFoundException(newSeat.getShowingId()));
    }

    @PutMapping("/showings/{id}/{row_nr}/{column_nr}/{name}/{surname}/{ticketType}")
    public Showing getReservation(
        @PathVariable("id") Long id,
        @PathVariable("row_nr") int row_nr,
        @PathVariable("column_nr") int column_nr,
        @PathVariable("name") String name,
        @PathVariable("surname") String surname,
        @PathVariable("ticketType") String ticketType) {

        // Retrieve showing based on movie title and room ID
        Showing showing = showings.findById(id).orElseThrow(() -> new ShowingNotFoundException(id));
        
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
            //User user = findByNameAndSurname(name, surname);
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