package com.multiplex.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Showing {
    private @Id @GeneratedValue Long id;
    private LocalDateTime showingTime;
    private String movieTitle;
    private int roomNr;

    @OneToMany(mappedBy = "showings", cascade = CascadeType.ALL)
    private List<Seat> seats;

    Showing() {
    }

    Showing(LocalDateTime showingTime, String movieTitle, int roomNr) {
        this.showingTime = showingTime;
        this.movieTitle = movieTitle;
        this.roomNr = roomNr;
        this.seats = generateSeats(10, 6);
    }

    // get
    public Long getId() {
        return this.id;
    }

    public LocalDateTime getShowingTime() {
        return this.showingTime;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }

    public int getRoomNr() {
        return this.roomNr;
    }

    public List<Seat> getList(){
        return this.seats;
    }

    // set
    public void setId(Long id) {
        this.id = id;
    }

    public void setShowingTime(LocalDateTime showingTime) {
        this.showingTime = showingTime;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setRoomNr(int roomNr) {
        this.roomNr = roomNr;
    }

    public void setList(List<Seat> seats){
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Showing))
            return false;

        Showing showing = (Showing) o;

        return Objects.equals(this.id, showing.id) && Objects.equals(this.showingTime, showing.showingTime)
                && Objects.equals(this.movieTitle, showing.movieTitle) && Objects.equals(this.roomNr, showing.roomNr);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.showingTime, this.movieTitle, this.roomNr, this.seats);
    }

    private List<Seat> generateSeats(int numRows, int numColumns) {
        List<Seat> generatedSeats = new ArrayList<>();

        for (int row = 1; row <= numRows; row++) {
            for (int column = 1; column <= numColumns; column++) {
                Seat seat = new Seat(row, column, false, this);
                seats.add(seat);
            }
        }

        return generatedSeats;
    }
}
