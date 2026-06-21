package com.skylink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeatManagerTest {

    private SeatManager seatManager;

    @BeforeEach
    void setUp() {
        seatManager = new SeatManager();
    }

    @Test
    void testAllocateSeat() {
        int seatId = seatManager.allocateSeat();
        assertNotNull(seatId);
        assertEquals(1, seatId);
    }

    @Test
    void testAllocateSeatMultipleTimes() {
        int seatId1 = seatManager.allocateSeat();
        int seatId2 = seatManager.allocateSeat();
        assertNotNull(seatId1);
        assertNotNull(seatId2);
        assertNotEquals(seatId1, seatId2);
    }

    @Test
    void testFreeSeat() {
        int seatId = seatManager.allocateSeat();
        seatManager.freeSeat(seatId);
        int newSeatId = seatManager.allocateSeat();
        assertEquals(seatId, newSeatId);
    }

    @Test
    void testFreeNonExistentSeat() {
        assertThrows(IllegalArgumentException.class, () -> {
            seatManager.freeSeat(100);
        });
    }
}