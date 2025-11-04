
# Sky-Link Airline Reservation System

Sky-Link is a Java-based Airline Reservation System with a web UI and REST API. It demonstrates OOP, in-memory data structures, and safe concurrent seat booking.

## Features
- Efficient flight search, seat allocation, and booking management using HashMap and concurrent data structures
- Synchronized seat booking to prevent double bookings and race conditions
- Optimized O(1) data lookups
- REST API and HTML/JS web UI for user interaction

## How to Run Locally
1. Ensure you have Java 17+ and Maven installed.
2. Build the project:
   ```sh
   mvn clean package
   ```
3. Run the web server:
   ```sh
   mvn exec:java -Dexec.mainClass="com.skylink.SkyLinkServer"
   ```
4. Open your browser and go to [http://localhost:4567](http://localhost:4567)

## Deploying the UI for Free
- Deploy the `src/main/resources/public` folder to GitHub Pages or Netlify for free static hosting.
- Deploy the Java backend (SkyLinkServer) to a free Java host (e.g., Render, Railway, Oracle Cloud Free Tier).
- Update the frontend API URLs if backend is hosted remotely.

## Project Structure
- `Flight.java`: Represents a flight and manages seats
- `Seat.java`: Represents a seat with atomic booking
- `BookingManager.java`: Handles flight management and safe booking
- `SkyLinkServer.java`: REST API and static web server
- `public/index.html`: Web UI for users

## Concurrency
- Uses `ConcurrentHashMap`, `ConcurrentLinkedQueue`, and `AtomicBoolean` for thread safety
- Synchronized seat booking prevents double booking in parallel scenarios

## License
MIT
