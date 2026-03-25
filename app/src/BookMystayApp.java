import java.util.*;

public class BookMystayApp {

    // Reservation class (only needed fields for this output)
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

    // Booking History stores reservations in order
    static class BookingHistory {
        List<Reservation> history = new ArrayList<>();

        public void addBooking(Reservation r) {
            history.add(r);
        }

        public List<Reservation> getHistory() {
            return history;
        }
    }

    // Main Program
    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();

        // Adding bookings
        bookingHistory.addBooking(new Reservation("Abhi", "Single"));
        bookingHistory.addBooking(new Reservation("Subha", "Double"));
        bookingHistory.addBooking(new Reservation("Vanmathi", "Suite"));

        // Output format exactly as shown
        System.out.println("Booking History and Reporting\n");
        System.out.println("Booking History Report");

        for (Reservation r : bookingHistory.getHistory()) {
            System.out.println(r);
        }
    }
}