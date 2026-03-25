import java.io.*;
import java.util.*;

// Serializable class to store full system state
class SystemState implements Serializable {
    Map<String, Integer> roomInventory;
    List<String> bookingHistory;

    public SystemState(Map<String, Integer> roomInventory, List<String> bookingHistory) {
        this.roomInventory = roomInventory;
        this.bookingHistory = bookingHistory;
    }
}

public class BookMystayApp {

    private static final String FILE_NAME = "system_state.dat";

    private static Map<String, Integer> roomInventory = new HashMap<>();
    private static List<String> bookingHistory = new ArrayList<>();

    // ---------------- PERSISTENCE METHODS ---------------- //

    // Save system state to file
    private static void saveSystemState() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            SystemState state = new SystemState(roomInventory, bookingHistory);
            out.writeObject(state);
            System.out.println("\n[✔] System state saved successfully.\n");
        } catch (Exception e) {
            System.out.println("\n[✘] Error saving system state: " + e.getMessage());
        }
    }

    // Load system state from file
    private static void loadSystemState() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("[!] No previous data found. Starting with default state.");
            initializeDefaultState();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) in.readObject();
            roomInventory = state.roomInventory;
            bookingHistory = state.bookingHistory;

            System.out.println("[✔] Previous system state loaded successfully.");
        } catch (Exception e) {
            System.out.println("[✘] Error loading saved data. Starting with default safe state.");
            initializeDefaultState();
        }
    }

    // Set default state if no persistence is found
    private static void initializeDefaultState() {
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
        roomInventory.put("Suite", 1);
        bookingHistory.clear();
    }

    // ---------------- APPLICATION LOGIC ---------------- //

    private static void simulateBooking(String guest, String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);

        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            bookingHistory.add("Guest: " + guest + ", Room Type: " + roomType);
            System.out.println("Booking Successful → " + guest + " (" + roomType + ")");
        } else {
            System.out.println("Booking Failed → No " + roomType + " rooms left (Guest: " + guest + ")");
        }
    }

    // ---------------- MAIN ---------------- //

    public static void main(String[] args) {

        System.out.println("\n=== Book My Stay App — Use Case 12: Data Persistence & Recovery ===\n");

        // Step 1: Load previous state
        loadSystemState();

        // Display current recovered state
        System.out.println("\nCurrent Inventory After Recovery:");
        System.out.println(roomInventory);

        System.out.println("\nBooking History After Recovery:");
        if (bookingHistory.isEmpty()) System.out.println("(No previous bookings)");
        else bookingHistory.forEach(System.out::println);

        // Step 2: Simulate new bookings
        System.out.println("\n\n--- New Bookings ---");
        simulateBooking("Abhi", "Single");
        simulateBooking("Subha", "Double");
        simulateBooking("Vanmathi", "Suite");

        // Step 3: Save state before exit
        saveSystemState();

        // Final summary
        System.out.println("--- Final Booking History ---");
        bookingHistory.forEach(System.out::println);
    }
}