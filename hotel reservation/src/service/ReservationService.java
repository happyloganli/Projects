package service;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;
import java.util.*;

public class ReservationService {
    public static ReservationService reservationService = new ReservationService();
    private ReservationService(){}
    public void addRoom(IRoom room){
        Room.allRooms.put(room.getRoomNumber(), room);
    }
    public IRoom getARoom(String roomId){
        return Room.allRooms.getOrDefault(roomId, null);
    }
    public Collection<IRoom> getAllRooms(){
        return Room.allRooms.values();
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        HashSet<Reservation> reserveList = Reservation.roomReservations.getOrDefault(room.getRoomNumber(), new HashSet<>());
        reserveList.add(reservation);
        Reservation.roomReservations.put(room.getRoomNumber(), reserveList);
        reserveList = Reservation.customerReservations.getOrDefault(customer.getEmail(), new HashSet<>());
        reserveList.add(reservation);
        Reservation.customerReservations.put(customer.getEmail(), reserveList);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        HashSet<IRoom> availableRooms = new HashSet<>();
        for (String roomId : Room.allRooms.keySet()){
            HashSet<Reservation> reserveList = Reservation.roomReservations.getOrDefault(roomId, new HashSet<>());
            boolean available = true;
            for(Reservation reservation: reserveList){
                if(!checkOutDate.before(reservation.getCheckInDate()) && !checkInDate.after(reservation.getCheckOutDate())){
                    available = false;
                    break;
                }
            }
            if(available){
                availableRooms.add(getARoom(roomId));
            }
        }
        return availableRooms;
    }
    public Collection<Reservation> getCustomerReservation(String email){
        return Reservation.customerReservations.getOrDefault(email, null);
    }
    public HashMap<String, HashSet<Reservation>> getAllReservation(){
        return Reservation.roomReservations;
    }
}
