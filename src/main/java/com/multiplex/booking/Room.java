package com.multiplex.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    private @Id @GeneratedValue Long id;
    private int roomNr;
    private int nrOfRows;
    private int nrOfColumns;

    // @OneToMany(mappedBy = "room")
    // public List<Showing> showings = new ArrayList<>();
    
    public Room(){
    }

    public Room(int roomNr, int nrOfRows, int nrOfColumns) {
        this.roomNr = roomNr;
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
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

}
