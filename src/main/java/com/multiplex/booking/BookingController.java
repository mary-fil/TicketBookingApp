package com.multiplex.booking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

@RestController
class BookingController {
    private final ShowingRepository showings;
    private final RoomRepository rooms;
    static final int TIME_INTERVAL = 2;
    static final double ADULT_PRICE = 25;
    static final double STUDENT_PRICE = 18;
    static final double CHILD_PRICE = 12.5;

    BookingController(ShowingRepository showings, RoomRepository rooms) {
        this.showings = showings;
        this.rooms = rooms;
    }

    @Operation(summary="Return a list of all showings")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Returned the list of showings", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Showing.class)) }),
        @ApiResponse(responseCode = "404", description = "List of showings not found", 
            content = @Content) })
    @GetMapping("/showings")
    List<Showing> allShowings() {
        Sort sorted = Sort.by(Sort.Direction.ASC, "movieTitle", "showingTime");
        return showings.findAll(sorted);
    }

    @Operation(summary="Return a list of all rooms")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Returned the list of rooms", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Room.class)) }),
        @ApiResponse(responseCode = "404", description = "List of rooms not found", 
            content = @Content) })
    @GetMapping("/rooms")
    List<Room> allRooms() {
        return rooms.findAll();
    }

    @Operation(summary="Create a new showing")
    @PostMapping("/showings")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Created new showing", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Showing.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid json supplied", 
            content = @Content)})
    Showing newShowing(@RequestBody Showing newShowing) {
        Room room = rooms.findById(newShowing.getRoom().getId())
            .orElseThrow(() -> new RoomNotFoundException(newShowing.getRoom().getId()));
        newShowing.setRoom(room);
        newShowing.generateSeats(room.getNrOfRows(),room.getNrOfColumns());
        return showings.save(newShowing);
    }

    @Operation(summary="Create a new room")
    @PostMapping("/rooms")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Created new room", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Room.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid json supplied", 
            content = @Content)})
    Room newRoom(@RequestBody Room newRoom) {
        return rooms.save(newRoom);
    }

    @Operation(summary="Update showing")
    @PutMapping("/showingsById/{id}")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Updated the showing", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Showing.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Showing not found", 
            content = @Content) })
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

    @Operation(summary="Delete showing")
    @DeleteMapping("/showingsById/{id}")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Deleted the showing", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Showing.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Showing not found", 
            content = @Content) })
    void deleteShowing(@PathVariable Long id) {
        showings.deleteById(id);
    }

    @Operation(summary="Return a list of showings starting at a specified time")
    @GetMapping("/showingsByTime/{dateTime}")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Found the showing", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Showing.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid date supplied", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Showing not found", 
            content = @Content) })
    public List<Showing> getShowingByTime(@PathVariable("dateTime") LocalDateTime dateTime) {
        return showings.findByShowingTime(dateTime);
    }

    // system lists movies available in the given time interval
    // variable TIME_INTERVAL -> chosen time interval
    @Operation(summary="Return a list of showings starting in the time interval of +/- 2 hours")
    @GetMapping("/showingsByInterval/{dateTime}")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Found the showings in the time interval of +/- 2 hours", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Showing.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid date supplied", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Showings not found", 
            content = @Content) })
    public List<Showing> getShowingsWithinTimeRange(@PathVariable("dateTime") LocalDateTime dateTime) {
        LocalDateTime startTime = dateTime.minusHours(TIME_INTERVAL);
        LocalDateTime endTime = dateTime.plusHours(TIME_INTERVAL);
        return showings.findByShowingTimeBetween(startTime, endTime);
    }

    // user chooses a particular screening
    @Operation(summary="Return a showing specified by id")
    @GetMapping("/showingsById/{id}")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Found the showing", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Showing.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Showing not found", 
            content = @Content) })
    Showing one(@PathVariable Long id) {

        return showings.findById(id).orElseThrow(() -> new ShowingNotFoundException(id));
    }

    @GetMapping("/seatByShowingIdRowColumn/{id}/{rowNr}/{columnNr}")
    Seat one(@PathVariable Long id, @PathVariable int rowNr, @PathVariable int columnNr) {

        Showing showing = showings.findById(id) .orElseThrow(() -> new ShowingNotFoundException(id));

        Set<Seat> seats = showing.getSeats();
        Seat foundSeat = new Seat();

        for (Seat a : seats) {
            if(a.getrowNr() == rowNr && a.getcolumnNr() == columnNr) foundSeat = a;
        }

        return foundSeat;
    }

    // user chooses seats, and gives the name of the person doing the reservation
    // (name and surname)
    @Operation(summary="Reservation of seat(s) by id. Id of seat, name, surname and ticket type is required.")
    @PutMapping("/ReservationOfSeats")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Created new reservation", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Room.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid json supplied", 
            content = @Content)})
    ReservationResponse replaceSeat(@Valid @RequestBody List<Seat> newSeats) {

        Showing showing = showings.findById(newSeats.get(0).getShowingId())
        .orElseThrow(() -> new ShowingNotFoundException(newSeats.get(0).getShowingId()));

        // check time, reservation should be before the movie
        Duration duration = Duration.between(LocalDateTime.now(),showing.getShowingTime());
        long minutes = duration.toMinutes();

        if(minutes <= 15) throw new CannotReserveException();

        Set<Seat> seats = showing.getSeats();

        double total = 0;

        for (Seat newSeat : newSeats) {
            Optional<Seat> seatOptional = seats.stream()
                .filter(seat -> seat.getId().equals(newSeat.getId()))
                .findFirst();

            if (seatOptional.isPresent()) {
                Seat seat = seatOptional.get();

                String name = newSeat.getName();
                String surname = newSeat.getSurname();
                String ticketType = newSeat.getTicketType();

                // seat cannot be reserved, name, surname and ticket type is required
                if(seat.isReserved()) throw new WrongInputException();
                if(name == null) throw new WrongInputException();
                if(surname == null) throw new WrongInputException();
                if(ticketType == null) throw new WrongInputException();

                // name must start with an uppercase
                if(!Character.isUpperCase(name.charAt(0))) throw new WrongInputException();

                // surname must start with an uppercase and could consist of two parts separated with
                // a single dash, in this case the second part should also start with a capital letter
                if(surname.contains("-")){
                    String[] surname_parts = surname.split("-");
                    String first_surname = surname_parts[0];
                    String second_surname = surname_parts[1];
                    if(!Character.isUpperCase(first_surname.charAt(0))) throw new WrongInputException();
                    if(!Character.isUpperCase(second_surname.charAt(0))) throw new WrongInputException();
                }
                else{
                    if(!Character.isUpperCase(surname.charAt(0))) throw new WrongInputException();
                }

                seat.setReserved(true);
                seat.setName(name);
                seat.setSurname(surname);
                seat.setTicketType(ticketType);

                if(newSeat.getTicketType().equals("adult")) total += ADULT_PRICE;
                if(newSeat.getTicketType().equals("student")) total += STUDENT_PRICE;
                if(newSeat.getTicketType().equals("child")) total += CHILD_PRICE;

            } else {
                throw new SeatNotFoundException(newSeat.getId());
            }
        }

        if(!showing.isValid()) throw new CannotReserveException();

        showings.save(showing);
        
        ReservationResponse response = new ReservationResponse();
        response.setTotal(total);
        response.setReservationExpirationTime(showing.getShowingTime().minusMinutes(15));
        
        return response;
    }

}