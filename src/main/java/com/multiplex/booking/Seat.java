package com.multiplex.booking;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Seat {
    private @Id @GeneratedValue Long id;
    private int row;
    private int column;
    // do zmiany na imie i nazwisko uzytkownika?
    private boolean reserved;

    @ManyToOne(fetch = FetchType.LAZY)
    private Showing showing;

    public Seat() {
    }

    public Seat(int row, int column, boolean reserved, Showing showing) {
        this.row = row;
        this.column = column;
        this.reserved = reserved;
        this.showing = showing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Showing getShowings() {
        return showing;
    }

    public void setShowings(Showing showing) {
        this.showing = showing;
    }
}
