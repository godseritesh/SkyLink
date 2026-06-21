package com.skylink;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

public class BookingManagerTest {
    private BookingManager manager;
    private Flight flight;

    @BeforeEach
    void setUp() {
        manager = new BookingManager();
        flight = new Flight("SKY123", "NYC", "LAX", new Date(), 252);
        manager.addFlight(flight);
    }

    @Test
    void testAddFlight() {
        Flight found = manager.searchFlight("SKY123");
        assertNotNull(found);
        assertEquals("SKY123", found.getFlightNumber());
    }

    @Test
    void testSearchFlights() {
        Flight flight2 = new Flight("SKY456", "NYC", "SFO", new Date(), 252);
        manager.addFlight(flight2);

        List<Flight> results = manager.searchFlights("NYC", "LAX");
        assertEquals(1, results.size());
        assertEquals("SKY123", results.get(0).getFlightNumber());
    }

    @Test
    void testSearchFlightsPartialMatch() {
        Flight flight2 = new Flight("SKY456", "NYC", "SFO", new Date(), 252);
        manager.addFlight(flight2);

        List<Flight> results = manager.searchFlights("NYC", null);
        assertEquals(2, results.size());
    }

    @Test
    void testRequestBooking() {
        BookingManager.BookingResult result = manager.requestBooking("SKY123", "01A", "John Doe");
        assertTrue(result.success());
        assertNotNull(result.booking());
        assertEquals("SKY123", result.booking().getFlightNumber());
        assertEquals("01A", result.booking().getSeatNumber());
        assertEquals("John Doe", result.booking().getPassengerName());
    }

    @Test
    void testDoubleBooking() {
        manager.requestBooking("SKY123", "01A", "John Doe");
        BookingManager.BookingResult result = manager.requestBooking("SKY123", "01A", "Jane Doe");
        assertFalse(result.success());
        assertEquals("Seat already booked", result.message());
    }

    @Test
    void testBookingInvalidFlight() {
        BookingManager.BookingResult result = manager.requestBooking("INVALID", "01A", "John Doe");
        assertFalse(result.success());
        assertEquals("Flight not found", result.message());
    }

    @Test
    void testBookingInvalidSeat() {
        BookingManager.BookingResult result = manager.requestBooking("SKY123", "99Z", "John Doe");
        assertFalse(result.success());
        assertEquals("Seat not found", result.message());
    }

    @Test
    void testCancelBooking() {
        BookingManager.BookingResult result = manager.requestBooking("SKY123", "01A", "John Doe");
        String bookingId = result.booking().getBookingId();
        
        assertTrue(manager.cancelBooking(bookingId));
        assertNull(manager.getBooking(bookingId));
        
        // Seat should be available again
        assertFalse(flight.getSeat("01A").isBooked());
    }

    @Test
    void testGetBookingsByPassenger() {
        manager.requestBooking("SKY123", "01A", "John Doe");
        manager.requestBooking("SKY123", "01B", "John Doe");
        manager.requestBooking("SKY123", "01C", "Jane Doe");

        List<Booking> johnBookings = manager.getBookingsByPassenger("John Doe");
        assertEquals(2, johnBookings.size());
    }

    @Test
    void testGetAllFlights() {
        Flight flight2 = new Flight("SKY456", "SFO", "LAX", new Date(), 252);
        manager.addFlight(flight2);

        List<Flight> allFlights = manager.getAllFlights();
        assertEquals(2, allFlights.size());
    }

    @Test
    void testAddMultipleFlights() {
        Flight flight2 = new Flight("SKY456", "NYC", "SFO", new Date(), 252);
        Flight flight3 = new Flight("SKY789", "LAX", "SFO", new Date(), 252);
        manager.addFlight(flight2);
        manager.addFlight(flight3);

        List<Flight> allFlights = manager.getAllFlights();
        assertEquals(3, allFlights.size());
    }

    @Test
    void testSearchFlightsWithMultipleResults() {
        Flight flight2 = new Flight("SKY456", "NYC", "SFO", new Date(), 252);
        Flight flight3 = new Flight("SKY789", "LAX", "SFO", new Date(), 252);
        manager.addFlight(flight2);
        manager.addFlight(flight3);

        List<Flight> results = manager.searchFlights("NYC", "SFO");
        assertEquals(2, results.size());
    }

    @Test
    void testSearchFlightsWithNoResults() {
        Flight flight2 = new Flight("SKY456", "NYC", "SFO", new Date(), 252);
        manager.addFlight(flight2);

        List<Flight> results = manager.searchFlights("LAX", "SFO");
        assertEquals(0, results.size());
    }

    @Test
    void testRequestBookingWithMultipleSeats() {
        BookingManager.BookingResult result = manager.requestBooking("SKY123", "01A", "John Doe");
        assertTrue(result.success());
        assertNotNull(result.booking());
        assertEquals("SKY123", result.booking().getFlightNumber());
        assertEquals("01A", result.booking().getSeatNumber());
        assertEquals("John Doe", result.booking().getPassengerName());

        result = manager.requestBooking("SKY123", "01B", "Jane Doe");
        assertTrue(result.success());
        assertNotNull(result.booking());
        assertEquals("SKY123", result.booking().getFlightNumber());
        assertEquals("01B", result.booking().getSeatNumber());
        assertEquals("Jane Doe", result.booking().getPassengerName());
    }

    @Test
    void testCancelMultipleBookings() {
        BookingManager.BookingResult result1 = manager.requestBooking("SKY123", "01A", "John Doe");
        BookingManager.BookingResult result2 = manager.requestBooking("SKY123", "01B", "Jane Doe");

        String bookingId1 = result1.booking().getBookingId();
        String bookingId2 = result2.booking().getBookingId();

        assertTrue(manager.cancelBooking(bookingId1));
        assertTrue(manager.cancelBooking(bookingId2));

        assertNull(manager.getBooking(bookingId1));
        assertNull(manager.getBooking(bookingId2));

        // Seats should be available again
        assertFalse(flight.getSeat("01A").isBooked());
        assertFalse(flight.getSeat("01B").isBooked());
    }

    // New test for the BookingManager class
    @Test
    void testUpdateFlight