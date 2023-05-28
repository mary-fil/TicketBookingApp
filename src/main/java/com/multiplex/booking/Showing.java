package com.multiplex.booking;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "showings")
public class Showing {
    private @Id @GeneratedValue Long id;
    private LocalDateTime showingTime;
    private String movieTitle;
    private int roomNr;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "showing_id", referencedColumnName = "id")
    private Set<Seat> seats;

    Showing() {
    }

    Showing(LocalDateTime showingTime, String movieTitle, int roomNr) {
        this.showingTime = showingTime;
        this.movieTitle = movieTitle;
        this.roomNr = roomNr;
        this.seats = generateSeats(2, 8);
    }

    private Set<Seat> generateSeats(int numRows, int numColumns) {
        Set<Seat> generatedSeats = new HashSet<>();

        for (int row = 1; row <= numRows; row++) {
            for (int column = 1; column <= numColumns; column++) {
                Seat seat = new Seat(row, column, false, this.getId());
                generatedSeats.add(seat);
            }
        }

        return generatedSeats;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getShowingTime() {
        return showingTime;
    }

    public void setShowingTime(LocalDateTime showingTime) {
        this.showingTime = showingTime;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getRoomNr() {
        return roomNr;
    }

    public void setRoomNr(int roomNr) {
        this.roomNr = roomNr;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }


}
