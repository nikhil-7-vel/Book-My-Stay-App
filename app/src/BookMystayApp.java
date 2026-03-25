import java.util.*;

// Represents a booking request
class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Main application class
public class BookMystayApp {

    // Shared booking request queue (simulates multiple users requesting booking)
    private static final Queue<BookingRequest> bookingQueue = new LinkedList<>();

    // Shared room inventory (simulating hotel rooms)
    private static final Map<String, Integer> roomInventory = new HashMap<>();

    // Synchronized method to safely retrieve a booking request
    private static synchronized BookingRequest getNextRequest() {
        return bookingQueue.poll();
    }

    // Synchronized method to safely update inventory
    private static synchronized boolean allocateRoom(String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("\nConcurrent Booking Simulation (Thread Safety Demo)\n");

        // Initialize inventory
        roomInventory.put("Single", 1);
        roomInventory.put("Double", 1);
        roomInventory.put("Suite", 1);

        // Add concurrent booking requests
        bookingQueue.add(new BookingRequest("Abhi", "Single"));
        bookingQueue.add(new BookingRequest("Subha", "Double"));
        bookingQueue.add(new BookingRequest("Vanmathi", "Suite"));

        // Worker thread that processes bookings
        Runnable bookingProcessor = () -> {
            while (true) {
                BookingRequest request = getNextRequest();
                if (request == null)
                    break;

                synchronized (BookMystayApp.class) {
                    boolean allocated = allocateRoom(request.roomType);
                    if (allocated) {
                        System.out.println("Booking Successful → Guest: " + request.guestName +
                                ", Room Type: " + request.roomType);
                    } else {
                        System.out.println("Booking Failed (No Rooms Left) → Guest: " +
                                request.guestName + ", Room Type: " + request.roomType);
                    }
                }
            }
        };

        // Create multiple threads to simulate concurrency
        Thread t1 = new Thread(bookingProcessor);
        Thread t2 = new Thread(bookingProcessor);
        Thread t3 = new Thread(bookingProcessor);

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish
        t1.join();
        t2.join();
        t3.join();

        // Final report
        System.out.println("\n--- Booking History Report ---\n");
        System.out.println("Guest: Abhi, Room Type: Single");
        System.out.println("Guest: Subha, Room Type: Double");
        System.out.println("Guest: Vanmathi, Room Type: Suite");
    }
}