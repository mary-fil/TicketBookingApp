package com.multiplex.booking;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "showings")
public class Showing {
    private @Id @GeneratedValue Long id;
    private LocalDateTime showingTime;
    private String movieTitle;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "showing_id", referencedColumnName = "id")
    private Set<Seat> seats;

    Showing() {
    }

    Showing(LocalDateTime showingTime, String movieTitle, Room room) {
        //room.showings.add(this);

        this.showingTime = showingTime;
        this.movieTitle = movieTitle;
        this.room = room;
        this.seats = generateSeats(room.getNrOfRows(), room.getNrOfColumns());
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
                && Objects.equals(this.movieTitle, showing.movieTitle) && Objects.equals(this.room, showing.room);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.showingTime, this.movieTitle, this.room, this.seats);
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

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
