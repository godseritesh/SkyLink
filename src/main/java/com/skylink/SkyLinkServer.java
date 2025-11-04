package com.skylink;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.*;

public class SkyLinkServer {
    private static final BookingManager manager = new BookingManager();
    private static final Gson gson = new Gson();

    public static void main(String[] args) {

        // Add 24 dummy flights for the UI schedule
        String[] cities = {"NYC", "LAX", "ORD", "DFW", "ATL", "DEN", "SEA", "MIA", "BOS", "SFO"};
        Random rand = new Random();
        int hour = 0;
        for (int i = 0; i < 24; i++) {
            String from = cities[rand.nextInt(cities.length)];
            String to;
            do { to = cities[rand.nextInt(cities.length)]; } while (to.equals(from));
            int depHour = (hour + rand.nextInt(2)) % 24;
            int depMin = rand.nextInt(60);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, depHour);
            cal.set(Calendar.MINUTE, depMin);
            cal.set(Calendar.SECOND, 0);
            String flightNum = "SKY" + (100 + i);
            Flight f = new Flight(flightNum, from, to, cal.getTime(), 100);
            manager.addFlight(f);
            hour = (depHour + 1) % 24;
        }
        // Always include SKY123 for demo
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Flight demoFlight = new Flight("SKY123", "NYC", "LAX", cal.getTime(), 100);
        manager.addFlight(demoFlight);

        port(4567);
        staticFiles.location("/public"); // Serve static frontend from resources/public

        // API: List all flights
        get("/api/flights", (req, res) -> {
            res.type("application/json");
            return gson.toJson(manager.searchFlights(req.queryParams("origin"), req.queryParams("destination")));
        });

        // API: Get flight by number
        get("/api/flight/:flightNumber", (req, res) -> {
            res.type("application/json");
            Flight f = manager.searchFlight(req.params("flightNumber"));
            return f == null ? "{}" : gson.toJson(f);
        });

        // API: Book a seat
        post("/api/book", (req, res) -> {
            Map<String, String> data = gson.fromJson(req.body(), Map.class);
            boolean result = manager.requestBooking(
                data.get("flightNumber"),
                data.get("seatNumber"),
                data.get("passengerName")
            );
            res.type("application/json");
            return gson.toJson(Map.of("success", result));
        });
    }
}
