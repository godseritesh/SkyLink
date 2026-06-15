package com.skylink;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class BookingTest {
    @Test
    void testBookingCreation() {
        Booking booking = new Booking("SKY123", "001", "John Doe");
        
        assertNotNull(booking.getBookingId());
        assertEquals(8, booking.getBookingId().length());
        assertEquals("SKY123", booking.getFlightNumber());
        assertEquals("001", booking.getSeatNumber());
        assertEquals("John Doe", booking.getPassengerName());
        assertFalse(booking.isCancelled());
        assertNotNull(booking.getBookingDate());
    }

    @Test
    void testBookingWithAllFields() {
        Date date = new Date();
        Booking booking = new Booking("BOOK123", "SKY456", "002", "Jane Doe", date, false);
        
        assertEquals("BOOK123", booking.getBookingId());
        assertEquals("SKY456", booking.getFlightNumber());
        assertEquals("002", booking.getSeatNumber());
        assertEquals("Jane Doe", booking.getPassengerName());
        assertEquals(date, booking.getBookingDate());
        assertFalse(booking.isCancelled());
    }

    @Test
    void testBookingCancellation() {
        Booking booking = new Booking("CANCEL123", "001", "Alice Smith", true);
        
        assertEquals("CANCEL123", booking.getBookingId());
        assertEquals("001", booking.getSeatNumber());
        assertEquals("Alice Smith", booking.getPassengerName());
        assertTrue(booking.isCancelled());
        assertNotNull(booking.getBookingDate());
    }

    @Test
    void testBookingWithFutureDate() {
        Date futureDate = new Date(System.currentTimeMillis() + 10000);
        Booking booking = new Booking("FUTURE123", "SKY789", "003", "Bob Johnson", futureDate, false);
        
        assertEquals("FUTURE123", booking.getBookingId());
        assertEquals("SKY789", booking.getFlightNumber());
        assertEquals("003", booking.getSeatNumber());
        assertEquals("Bob Johnson", booking.getPassengerName());
        assertEquals(futureDate, booking.getBookingDate());
        assertFalse(booking.isCancelled());
    }
}