package com.skylink;

import java.util.*;
import java.util.concurrent.*;

public class Flight {
    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final Date departureTime;
    private final Map<String, Seat> seats; // seatNumber -> Seat

    public Flight(String flightNumber, String origin, String destination, Date departureTime, int seatCount) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.seats = new ConcurrentHashMap<>();
        for (int i = 1; i <= seatCount; i++) {
            String seatNum = String.format("%03d", i);
            seats.put(seatNum, new Seat(seatNum));
        }
    }

    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public Date getDepartureTime() { return departureTime; }
    public Map<String, Seat> getSeats() { return seats; }

    public Seat getSeat(String seatNumber) {
        return seats.get(seatNumber);
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> available = new ArrayList<>();
        for (Seat seat : seats.values()) {
            if (!seat.isBooked()) {
                available.add(seat);
            }
        }
        return available;
    }
}
