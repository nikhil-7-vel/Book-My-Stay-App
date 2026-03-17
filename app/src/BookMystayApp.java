import java.util.*;

// Search Service
class RoomSearchService {

    public void searchRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("===== Room Search Results =====\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Show only rooms with availability
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available);
                System.out.println("--------------------------------------");
            }
        }
    }
}

// Main UC4 class
public class uc4RoomSearch {

    public static void main(String[] args) {

        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Use RoomInventory from UC3
        RoomInventory inventory = new RoomInventory();

        RoomSearchService service = new RoomSearchService();

        service.searchRooms(rooms, inventory);
    }
}