package model;

public class FreeRoom extends Room{
    public FreeRoom(String RoomNumber, Double price, RoomType enumeration) {
        super(RoomNumber, (double) 0, enumeration);
    }
}
