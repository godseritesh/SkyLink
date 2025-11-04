package com.skylink;

import java.util.*;
import java.util.concurrent.*;

public class BookingManager {
    private final Map<String, Flight> flights = new ConcurrentHashMap<>();
    private final Queue<BookingRequest> bookingQueue = new ConcurrentLinkedQueue<>();

    public void addFlight(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
    }

    public Flight searchFlight(String flightNumber) {
        return flights.get(flightNumber);
    }

    public List<Flight> searchFlights(String origin, String destination) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights.values()) {
            if (flight.getOrigin().equalsIgnoreCase(origin) && flight.getDestination().equalsIgnoreCase(destination)) {
                result.add(flight);
            }
        }
        return result;
    }

    public boolean requestBooking(String flightNumber, String seatNumber, String passengerName) {
        BookingRequest request = new BookingRequest(flightNumber, seatNumber, passengerName);
        bookingQueue.add(request);
        return processBooking(request);
    }

    private boolean processBooking(BookingRequest request) {
        Flight flight = flights.get(request.flightNumber());
        if (flight == null) return false;
        Seat seat = flight.getSeat(request.seatNumber());
        if (seat == null) return false;
        synchronized (seat) {
            return seat.book();
        }
    }

    public int getBookingQueueSize() {
        return bookingQueue.size();
    }

    private record BookingRequest(String flightNumber, String seatNumber, String passengerName) {}
}
