package com.multiplex.booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
class BookingController{
    private final ShowingRepository showings;
    final int hour = 1;

    BookingController(ShowingRepository showings){
        this.showings = showings;
    }

    // Aggregate root
    @GetMapping("/showings")
    List<Showing> all(){
        Sort sorted = Sort.by(Sort.Direction.ASC, "movieTitle", "showingTime");
        return showings.findAll(sorted);
    }

    @PostMapping("/showings")
    Showing newShowing(@RequestBody Showing newShowing){
        return showings.save(newShowing);
    }

    // Single item

    @GetMapping("/showings/{id}")
    Showing one(@PathVariable Long id){

        return showings.findById(id) .orElseThrow(() -> new ShowingNotFoundException(id));
    }

    @PutMapping("/showings/{id}")
    Showing replaceShowing(@RequestBody Showing newShowing, @PathVariable Long id){

        return showings.findById(id)
            .map(showing -> {
                showing.setShowingTime(newShowing.getShowingTime());
                showing.setMovieTitle(newShowing.getMovieTitle());
                showing.setRoomNr(newShowing.getRoomNr());
                return showings.save(showing);
            })
            .orElseGet(() ->{
                newShowing.setId(id);
                return showings.save(newShowing);
            });
    }

    @DeleteMapping("/showings/{id}")
    void deleteShowing(@PathVariable Long id){
        showings.deleteById(id);
    }

    @GetMapping("/showingsTime/{dateTime}")
    public List<Showing> getShowingByTime(@PathVariable("dateTime") LocalDateTime dateTime) {
        return showings.findByShowingTime(dateTime);
    }

    // testing time range
    @GetMapping("/showingsInterval/{dateTime}")
    public List<Showing> getShowingsWithinTimeRange(@PathVariable("dateTime") LocalDateTime dateTime) {
        LocalDateTime startTime = dateTime.minusHours(hour);
        LocalDateTime endTime = dateTime.plusHours(hour);
        return showings.findByShowingTimeBetween(startTime, endTime);
    }
}