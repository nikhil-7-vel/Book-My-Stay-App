import java.util.*;

public class BookMystayApp {

    // ----------------------- SERVICE CLASS -----------------------
    static class Service {
        private String serviceName;
        private double cost;

        public Service(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return serviceName + " (₹" + cost + ")";
        }
    }

    // ----------------------- RESERVATION CLASS -----------------------
    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomNumber;

        public Reservation(String reservationId, String guestName, String roomNumber) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomNumber = roomNumber;
        }

        public String getReservationId() {
            return reservationId;
        }

        @Override
        public String toString() {
            return "Reservation ID: " + reservationId +
                    ", Guest: " + guestName +
                    ", Room: " + roomNumber;
        }
    }

    // ------------------- ADD-ON SERVICE MANAGER -------------------
    static class AddOnServiceManager {

        // reservationID -> list of selected services
        private Map<String, List<Service>> serviceMap = new HashMap<>();

        // Add a service to a reservation
        public void addService(String reservationId, Service service) {
            serviceMap.computeIfAbsent(reservationId, id -> new ArrayList<>()).add(service);
        }

        // Get all services for a reservation
        public List<Service> getServices(String reservationId) {
            return serviceMap.getOrDefault(reservationId, new ArrayList<>());
        }

        // Calculate total cost
        public double calculateTotalServiceCost(String reservationId) {
            return getServices(reservationId).stream().mapToDouble(Service::getCost).sum();
        }
    }

    // --------------------------- MAIN ---------------------------
    public static void main(String[] args) {

        // Create an existing reservation
        Reservation reservation = new Reservation("R1001", "Arjun", "Room-205");

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        Service breakfast = new Service("Breakfast Buffet", 499.0);
        Service airportPickup = new Service("Airport Pickup", 999.0);
        Service spaAccess = new Service("Spa Access", 1499.0);

        // Guest selects services
        manager.addService(reservation.getReservationId(), breakfast);
        manager.addService(reservation.getReservationId(), airportPickup);
        manager.addService(reservation.getReservationId(), spaAccess);

        // Display reservation
        System.out.println(reservation);

        // Display selected services
        System.out.println("Selected Add-On Services:");
        for (Service s : manager.getServices(reservation.getReservationId())) {
            System.out.println("- " + s);
        }

        // Display total add-on cost
        double totalCost = manager.calculateTotalServiceCost(reservation.getReservationId());
        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);
    }
}