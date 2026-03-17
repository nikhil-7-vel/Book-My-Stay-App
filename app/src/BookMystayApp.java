abstract class Room {
    private String roomType;
    private int beds;
    private int size;
    private double price;

    // Constructor
    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Getters
    public String getRoomType() { return roomType; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }

    // Abstract method
    public abstract void displayRoomDetails();
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 250, 1000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sqft");
        System.out.println("Price per night: ₹" + getPrice());
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 400, 1800);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sqft");
        System.out.println("Price per night: ₹" + getPrice());
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 750, 3000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sqft");
        System.out.println("Price per night: ₹" + getPrice());
    }
}

// Main Class (UC2)
public class BookMystayApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("===== Room Details & Availability =====\n");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("--------------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("--------------------------------------");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("--------------------------------------");

        System.out.println("\nApplication Terminated.");
    }
}