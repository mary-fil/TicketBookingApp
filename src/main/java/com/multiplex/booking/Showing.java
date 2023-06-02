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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Class Showing that will be used to generate objects "showing"
 * Each showing will have its id, time of the showing and what title of movie which will be played.
 * Showing will be connected to the room where the movie will be played and set of seats that are in this room. Each seat stores futher information.
 * Set of seats will be generated based on the nr of columns and nr of rows of the room that was assigned to the showing.
 */
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

        this.showingTime = showingTime;
        this.movieTitle = movieTitle;
        this.room = room;
        this.seats = generateSeatsForConstructor(room.getNrOfRows(), room.getNrOfColumns());
    }

    public Set<Seat> generateSeatsForConstructor(int numRows, int numColumns) {
        Set<Seat> generatedSeats = new HashSet<>();

        for (int row = 1; row <= numRows; row++) {
            for (int column = 1; column <= numColumns; column++) {
                Seat seat = new Seat(row, column, false, this.getId());
                generatedSeats.add(seat);
            }
        }

        return generatedSeats;
    }

    public void generateSeats(int numRows, int numColumns) {
        Set<Seat> generatedSeats = new HashSet<>();

        for (int row = 1; row <= numRows; row++) {
            for (int column = 1; column <= numColumns; column++) {
                Seat seat = new Seat(row, column, false, this.getId());
                generatedSeats.add(seat);
            }
        }
        this.seats = generatedSeats;
    }

    boolean isValid() {

        // Assume the room is rectangular
        Seat[][] seatArray = new Seat[room.getNrOfRows()][room.getNrOfColumns()];

        for (int i = 1; i <= room.getNrOfRows(); i++) {
            for (int j = 1; j <= room.getNrOfColumns(); j++) {

                // Find a seat by column and by row
                for (Seat seat : seats) {
                    if (seat.getrowNr() == i && seat.getcolumnNr() == j) {
                        seatArray[i - 1][j - 1] = seat;
                        break;
                    }
                }
            }
        }

        // Find a seat not reserved and check if gap is 1
        for (int i = 0; i < room.getNrOfRows(); i++) {

            for (int j = 0; j < room.getNrOfColumns(); j++) {

                if (seatArray[i][j].isReserved() == false && j > 0 && j < room.getNrOfColumns() - 1) {
                    if (seatArray[i][j - 1].isReserved() && seatArray[i][j + 1].isReserved())
                        return false;
                }
            }
        }

        return true;
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
