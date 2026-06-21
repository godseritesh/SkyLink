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

    @Test
    public void testHandleRequest() {
        SkyLinkServer server = new SkyLinkServer();
        server.start();
        String response = server.handleRequest("Test Request");
        assertEquals("Expected Response", response, "Response should match the expected value");
        server.stop();
    }

    @Test
    public void testConcurrentRequests() throws InterruptedException {
        SkyLinkServer server = new SkyLinkServer();
        server.start();
        
        Runnable task = () -> {
            String response = server.handleRequest("Concurrent Request");
            assertEquals("Expected Response", response, "Response should match the expected value");
        };
        
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        
        thread1.start();
        thread2.start();
        
        thread1.join();
        thread2.join();
        
        server.stop();
    }
}