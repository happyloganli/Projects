package model;
import java.util.HashMap;
import java.util.HashSet;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;
    public static HashMap<String, IRoom> allRooms = new HashMap<>();
    public Room(String roomNumber, Double price, RoomType roomType){
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }
    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "RoomNumber: " + roomNumber + "  Price: $" + price + "  RoomType: " + roomType;
    }
}
