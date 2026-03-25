import java.util.*;

public class BookMystayApp {

    // ---------------- RESERVATION CLASS ----------------
    static class Reservation {
        String reservationId;
        String guestName;
        String roomType;
        String roomId;  // allocated room

        public Reservation(String reservationId, String guestName, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
        }

        @Override
        public String toString() {
            return "Reservation ID: " + reservationId +
                    ", Guest: " + guestName +
                    ", Room Type: " + roomType +
                    ", Room ID: " + roomId;
        }
    }

    // ---------------- INVENTORY ----------------
    static class RoomInventory {
        Map<String, Integer> count = new HashMap<>();

        public RoomInventory() {
            count.put("Single", 2);
            count.put("Double", 1);
            count.put("Suite", 1);
        }

        public void reduce(String type) {
            count.put(type, count.get(type) - 1);
        }

        public void restore(String type) {
            count.put(type, count.get(type) + 1);
        }
    }

    // ---------------- CANCELLATION SERVICE ----------------
    static class CancellationService {
        Stack<String> rollbackStack = new Stack<>();

        public void cancelBooking(String reservationId,
                                  Map<String, Reservation> activeBookings,
                                  RoomInventory inventory) {

            if (!activeBookings.containsKey(reservationId)) {
                System.out.println("Cancellation Failed: Reservation does not exist.");
                return;
            }

            // Get reservation
            Reservation r = activeBookings.get(reservationId);

            // Step 1: Push room ID into rollback stack
            rollbackStack.push(r.roomId);

            // Step 2: Restore inventory
            inventory.restore(r.roomType);

            // Step 3: Remove from active bookings
            activeBookings.remove(reservationId);

            System.out.println("Cancellation Successful → Released Room: " + r.roomId +
                    ", Restored Inventory for: " + r.roomType);
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        CancellationService cancelService = new CancellationService();

        // Active confirmed bookings
        Map<String, Reservation> activeBookings = new HashMap<>();

        // Add sample confirmed bookings
        Reservation r1 = new Reservation("R1001", "Abhi", "Single", "S1");
        Reservation r2 = new Reservation("R1002", "Subha", "Double", "D1");

        activeBookings.put(r1.reservationId, r1);
        activeBookings.put(r2.reservationId, r2);

        System.out.println("----- Active Bookings -----");
        for (Reservation r : activeBookings.values()) {
            System.out.println(r);
        }

        System.out.println("\n----- Performing Cancellations -----");

        // Valid cancellation
        cancelService.cancelBooking("R1001", activeBookings, inventory);

        // Invalid cancellation
        cancelService.cancelBooking("R9999", activeBookings, inventory);

        System.out.println("\n----- Remaining Active Bookings -----");
        for (Reservation r : activeBookings.values()) {
            System.out.println(r);
        }
    }
}