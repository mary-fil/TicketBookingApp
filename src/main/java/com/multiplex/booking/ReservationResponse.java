package com.multiplex.booking;

import java.time.LocalDateTime;

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
