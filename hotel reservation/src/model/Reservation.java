package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;
    public static HashMap<String, HashSet<Reservation>> roomReservations = new HashMap<String, HashSet<Reservation>>();
    public static HashMap<String, HashSet<Reservation>> customerReservations = new HashMap<String, HashSet<Reservation>>();
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
    public Date getCheckInDate(){
        return this.checkInDate;
    }
    public Date getCheckOutDate(){
        return this.checkOutDate;
    }
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return "Customer: " + customer.getName() +
                "  Room: " + room.getRoomNumber() + "  Date: " + dateFormat.format(checkInDate) +
                " - " + dateFormat.format(checkOutDate);
    }
}
