package com.skylink;

import java.util.concurrent.atomic.AtomicBoolean;

public class Seat {
    private final String seatNumber;
    private final AtomicBoolean booked;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.booked = new AtomicBoolean(false);
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return booked.get();
    }

    public boolean book() {
        return booked.compareAndSet(false, true);
    }
}
