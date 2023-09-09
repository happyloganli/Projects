package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class HotelResource {
    private HotelResource(){}
    public static HotelResource hotelResource = new HotelResource();

    public Customer getCustomer(String email){
        return CustomerService.customerService.getCustomer(email);
    }

    public Customer createACustomer(String email, String firstName, String lastName){
        return CustomerService.customerService.addCustomer(email, firstName, lastName);
    }
    public IRoom getRoom(String roomNumber){
        return ReservationService.reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = getCustomer(customerEmail);
        return ReservationService.reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }
    public Collection<Reservation> getCustomersReservations(String customerEmail){
        return ReservationService.reservationService.getCustomerReservation(customerEmail);
    }

    public Collection<IRoom>  findARoom(Date checkIn, Date checkOut){
        return ReservationService.reservationService.findRooms(checkIn, checkOut);
    }

}
