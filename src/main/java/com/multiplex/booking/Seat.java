package com.multiplex.booking;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Table(name = "seats")
public class Seat {
    private @Id @GeneratedValue Long id;
    private int row_nr;
    private int column_nr;
    // do zmiany na imie i nazwisko uzytkownika?
    private boolean reserved;
    private String name;
    private String surname;

    @Column(name = "showing_id")
    private Long showingId;

    public Seat() {
    }

    public Seat(int row_nr, int column_nr, boolean reserved, Long showingId) {
        this.row_nr = row_nr;
        this.column_nr = column_nr;
        this.reserved = reserved;
        this.showingId = showingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRow_nr() {
        return row_nr;
    }

    public void setRow_nr(int row_nr) {
        this.row_nr = row_nr;
    }

    public int getColumn_nr() {
        return column_nr;
    }

    public void setColumn_nr(int column_nr) {
        this.column_nr = column_nr;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Long getShowingId() {
        return showingId;
    }

    public void setShowingId(Long showingId) {
        this.showingId = showingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
