package com.multiplex.booking;

import java.time.LocalDateTime;

/**
 * When user makes a reservation object ReservationResponse will be returned by application
 * ReservationResponse has two attributes: total and reservationExpirationTime
 * total = total amount to pay for tickets
 * reservationExpirationTime = expiration time of the reservation (start of movie substracted by 15 minutes)
 */
class ReservationResponse {
    private double total;
    private LocalDateTime reservationExpirationTime;

    public ReservationResponse() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getReservationExpirationTime() {
        return reservationExpirationTime;
    }

    public void setReservationExpirationTime(LocalDateTime reservationExpirationTime) {
        this.reservationExpirationTime = reservationExpirationTime;
    }
}
