package com.multiplex.booking;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Class Seat that will be used to generate objects "seat"
 * Each seat will have its id, attributes used to store information where certain seat is placed within the room (rowNr and columnNr) and boolean to check if seat is reserved.
 * Seat will also store information about the person that reserved the seat (name and surname) as well as the type of ticket the person choose 
 * and information to which showing they are connected.
 * Requirements: 
 * - name and surname should each be at least three characters long, starting with a capital letter.
 * - the surname could consist of two parts separated with a single dash, in this case the second part should also start with a capital letter.
 */
@Entity
@Table(name = "seats")
public class Seat {
    private @Id @GeneratedValue Long id;
    private int rowNr;
    private int columnNr;
    private boolean reserved;

    @Size(min = 3, message = "Name must be at least 3 characters long")
    @Pattern(regexp = "^[A-Z].*", message = "Name must start with a capital letter")
    private String name;

    @Size(min = 3, message = "Surname must be at least 3 characters long")
    @Pattern(regexp = "^[A-Z][A-Za-z]*(?:-[A-Z][A-Za-z]*)?$", message = "Name must start with a capital letter")
    private String surname;

    private String ticketType;

    @Column(name = "showing_id")
    private Long showingId;

    public Seat() {
    }

    public Seat(int rowNr,int columnNr) {
        this.rowNr = rowNr;
        this.columnNr = columnNr;
    }

    public Seat(int rowNr, int columnNr, boolean reserved, Long showingId) {
        this.rowNr = rowNr;
        this.columnNr = columnNr;
        this.reserved = reserved;
        this.showingId = showingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getrowNr() {
        return rowNr;
    }

    public void setrowNr(int rowNr) {
        this.rowNr = rowNr;
    }

    public int getcolumnNr() {
        return columnNr;
    }

    public void setcolumnNr(int columnNr) {
        this.columnNr = columnNr;
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

        return Objects.equals(this.columnNr, seat.columnNr)
        && Objects.equals(this.rowNr, seat.rowNr);
    }
}
