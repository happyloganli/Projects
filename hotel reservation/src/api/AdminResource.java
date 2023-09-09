package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;
import service.CustomerService;
import service.ReservationService;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class AdminResource {
    public static AdminResource adminResource = new AdminResource();

    private AdminResource(){}
    public Customer getCustomer(String email){
        return CustomerService.customerService.getCustomer(email);
    }
    public void addRoom(Room room){
        ReservationService.reservationService.addRoom(room);
    }
    public Collection<IRoom> getAllRooms(){
        return ReservationService.reservationService.getAllRooms();
    }
    public Collection<Customer> getAllCustomers(){
        return CustomerService.customerService.getAllCustomers();
    }
    public HashMap<String, HashSet<Reservation>> getAllReservations(){
        return ReservationService.reservationService.getAllReservation();
    }
    public String isRoomIdValid(String roomId){
        if (ReservationService.reservationService.getARoom(roomId) != null)
        {
            return "201";
        } else {
            try {
                int intValue = Integer.parseInt(roomId);
                return "200";
            } catch (NumberFormatException e) {
                return "202";
            }
        }
            
        }

    }
