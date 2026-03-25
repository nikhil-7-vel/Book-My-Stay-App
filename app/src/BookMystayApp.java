import java.util.*;

public class BookMystayApp {

    // ---------------- CUSTOM EXCEPTION ----------------
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // ---------------- RESERVATION CLASS ----------------
    static class Reservation {
        String guestName;
        String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        @Override
        public String toString() {
            return "Guest: " + guestName + ", Room Type: " + roomType;
        }
    }

    // ---------------- ROOM INVENTORY ----------------
    static class RoomInventory {
        Map<String, Integer> rooms = new HashMap<>();

        public RoomInventory() {
            rooms.put("Single", 2);
            rooms.put("Double", 1);
            rooms.put("Suite", 1);
        }

        public boolean isRoomAvailable(String roomType) {
            return rooms.containsKey(roomType) && rooms.get(roomType) > 0;
        }

        public void reduceRoom(String roomType) {
            rooms.put(roomType, rooms.get(roomType) - 1);
        }
    }

    // ---------------- VALIDATOR ----------------
    static class BookingValidator {

        public static void validateInput(String guestName, String roomType, RoomInventory inventory)
                throws InvalidBookingException {

            if (guestName == null || guestName.isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }
            if (!inventory.rooms.containsKey(roomType)) {
                throw new InvalidBookingException("Invalid room type: " + roomType);
            }
            if (!inventory.isRoomAvailable(roomType)) {
                throw new InvalidBookingException("No rooms available for type: " + roomType);
            }
        }
    }

    // ---------------- MAIN PROGRAM ----------------
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        List<Reservation> confirmedBookings = new ArrayList<>();

        System.out.println("Booking Validation System\n");

        // Test bookings (some valid, some invalid)
        attemptBooking("Abhi", "Single", inventory, confirmedBookings);
        attemptBooking("", "Suite", inventory, confirmedBookings);                // invalid guest
        attemptBooking("Meera", "TripleRoom", inventory, confirmedBookings);      // invalid room
        attemptBooking("Rahul", "Double", inventory, confirmedBookings);
        attemptBooking("Kavi", "Double", inventory, confirmedBookings);           // no inventory

        // Display successful bookings
        System.out.println("\nConfirmed Bookings:");
        for (Reservation r : confirmedBookings) {
            System.out.println(r);
        }
    }

    // ---------------- METHOD TO ATTEMPT BOOKING ----------------
    public static void attemptBooking(String guest, String roomType,
                                      RoomInventory inventory,
                                      List<Reservation> confirmed) {

        try {
            BookingValidator.validateInput(guest, roomType, inventory);

            // If validation passed → confirm booking
            Reservation r = new Reservation(guest, roomType);
            confirmed.add(r);
            inventory.reduceRoom(roomType);

            System.out.println("Booking Successful → " + r);

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}
