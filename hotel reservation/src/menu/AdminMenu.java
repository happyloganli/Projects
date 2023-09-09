package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;


import java.util.*;

public class AdminMenu {
    static Scanner scanner;
    public static void adminMenu() {
        boolean keepRunning = true;
        do {
            System.out.println("          Admin Menu        ");
            System.out.println("----------------------------");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("----------------------------");
            String selection = scanner.nextLine();
            switch (selection) {
                case "1" -> {
                    displayCustomers();
                }
                case "2" -> {
                    displayAllRooms();
                }
                case "3" -> {
                    displayAllReservations();
                }
                case "4" -> {
                    addRoom();
                }
                case "5" -> {
                    keepRunning = false;
                }
                default -> System.out.println(selection + " is not a valid input. Plead enter a number from 1 to 5.");
            }
        } while (keepRunning);
    }
    public static void displayCustomers(){
        Collection<Customer> allCustomers = AdminResource.adminResource.getAllCustomers();
        System.out.println("             Customer List");
        System.out.println("-----------------------------------------\n");
        for(Customer customer: allCustomers){
            System.out.println(customer);
        }
        System.out.println("\n-----------------------------------------");
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }
    public static void addRoom(){
        boolean keepRunning = true;
        double price = 0;
        String roomId;
        do {
            while (true){
                System.out.println("Enter room ID: (Please Enter a Number)");
                roomId = scanner.nextLine();
                String errorNum = AdminResource.adminResource.isRoomIdValid(roomId);
                if(Objects.equals(errorNum,"200")){
                    break;
                }else{
                    if(Objects.equals(errorNum,"201")) {
                        System.out.println("room ID exists");
                    }else {
                        System.out.println("Please enter a number");
                    }
                }
            }
            while (true){
                System.out.println("Enter price per night:");
                try {
                    price = Double.parseDouble(scanner.nextLine());
                    if (price >= 0) {
                        break;
                    } else {
                        System.out.println("Please do not enter negative number.");
                    }
                } catch (Exception exception) {
                    System.out.println("Please enter a number");
                }
            }

            RoomType roomType = null;
            loop:
            while (true) {
                System.out.println("Enter room type(1 for single bed, 2 for double bed):");
                String selection = scanner.nextLine();
                switch (selection) {
                    case "1" -> {
                        roomType = RoomType.SINGLE;
                        break loop;
                    }
                    case "2" -> {
                        roomType = RoomType.DOUBLE;
                        break loop;
                    }
                    default -> System.out.println("Please enter 1 or 2:");
                }
            }
            Room newRoom = new Room(roomId, price, roomType);
            AdminResource.adminResource.addRoom(newRoom);
            System.out.println("Successfully add a Room!");
            loop:
            while (true) {
                System.out.println("Would you like to add another room? (y/n):");
                String selection = scanner.nextLine();
                switch (selection) {
                    case "y" -> {
                        break loop;
                    }
                    case "n" -> {
                        keepRunning = false;
                        break loop;
                    }
                    default -> System.out.println("Please enter y or n:");
                }
            }
        }while (keepRunning);
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }
    public static void displayAllRooms(){
        System.out.println("                    All Rooms");
        System.out.println("---------------------------------------------------\n");
        Collection<IRoom> allRooms = AdminResource.adminResource.getAllRooms();
        for(IRoom room: allRooms){
            System.out.println(room);
        }
        System.out.println("\n---------------------------------------------------");
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }
    public static void displayAllReservations(){
        HashMap<String, HashSet<Reservation>> allReservations = AdminResource.adminResource.getAllReservations();
        if (allReservations.isEmpty()){
            System.out.println("No reservation yet.");
        }else {
            System.out.println("                 Reservations");
            System.out.println("----------------------------------------------------\n");
            for (String roomId : allReservations.keySet()) {
                for (Reservation reservation : allReservations.getOrDefault(roomId, null)) {
                    System.out.println(reservation);
                }
            }
            System.out.println("\n----------------------------------------------------");
        }
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }
}
