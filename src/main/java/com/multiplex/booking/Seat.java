package com.multiplex.booking;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "seats")
public class Seat {
    private @Id @GeneratedValue Long id;
    private int row_nr;
    private int column_nr;
    private boolean reserved;

    @Size(min = 3, message = "Name must be at least 3 characters long")
    @Pattern(regexp = "^[A-Z].*", message = "Name must start with a capital letter")
    private String name;

    @Size(min = 3, message = "Surname must be at least 3 characters long")
    @Pattern(regexp = "^[A-Z].*", message = "Name must start with a capital letter")
    private String surname;

    private String ticketType;

    @Column(name = "showing_id")
    private Long showingId;

    public Seat() {
    }

    public Seat(int row_nr,int column_nr) {
        this.row_nr = row_nr;
        this.column_nr = column_nr;
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

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Seat))
            return false;
        Seat seat = (Seat) o;

        return Objects.equals(this.column_nr, seat.column_nr)
        && Objects.equals(this.row_nr, seat.row_nr);
    }
}
