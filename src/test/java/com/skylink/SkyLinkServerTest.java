package com.skylink;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SkyLinkServerTest {

    @Test
    public void testSkyLinkServerInitialization() {
        SkyLinkServer server = new SkyLinkServer();
        assertNotNull(server, "SkyLinkServer should not be null");
    }

    @Test
    public void testSkyLinkServerStartStop() {
        SkyLinkServer server = new SkyLinkServer();
        server.start();
        assertTrue(server.isRunning(), "Server should be running after start");
        server.stop();
        assertFalse(server.isRunning(), "Server should be stopped");
    }
}