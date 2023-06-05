package com.multiplex.booking;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Class Room that will be used to generate objects "room"
 * Each room will have its id, room number, number of rows and number of columns in the room
 */
@Entity
@Table(name = "rooms")
public class Room {
    private @Id @GeneratedValue Long id;
    private int roomNr;
    private int nrOfRows;
    private int nrOfColumns;

    public Room(){
    }

    public Room(int roomNr, int nrOfRows, int nrOfColumns) {
        this.roomNr = roomNr;
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoomNr() {
        return roomNr;
    }
    public void setRoomNr(int roomNr) {
        this.roomNr = roomNr;
    }
    public int getNrOfRows() {
        return nrOfRows;
    }
    public void setNrOfRows(int nrOfRows) {
        this.nrOfRows = nrOfRows;
    }
    public int getNrOfColumns() {
        return nrOfColumns;
    }
    public void setNrOfColumns(int nrOfColumns) {
        this.nrOfColumns = nrOfColumns;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Room))
            return false;

        Room room = (Room) o;

        return Objects.equals(this.id, room.id) && Objects.equals(this.nrOfColumns, room.nrOfColumns)
                && Objects.equals(this.nrOfRows, room.nrOfRows);

    }
}
