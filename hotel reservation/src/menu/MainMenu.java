package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    static Scanner scanner = new Scanner(System.in);
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public static void mainMenu() {
        loop: while (true) {
            System.out.println("          Main Menu        ");
            System.out.println("---------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("---------------------------");
            System.out.println("Please select a number for the menu option");
            String selection = scanner.nextLine();
            switch (selection) {
                case "1" -> {
                    reserveARoom();
                }
                case "2" -> {
                    seeMyReservations();
                }
                case "3" -> {
                    createAccount();
                }
                case "4" -> {
                    AdminMenu.scanner = scanner;
                    AdminMenu.adminMenu();
                }
                case "5" -> {
                    break loop;
                }
                default -> System.out.println(selection + " is not a valid input. Plead enter a number from 1 to 5.");
            }
        }
        scanner.close();
    }
    public static void reserveARoom() {
        Date checkInDate;
        Date checkOutDate;
        String email = null;
        boolean exit = false;
        do {
            try {
                System.out.println("Enter check in date mm/dd/yyyy example 08/16/2023");
                checkInDate = new SimpleDateFormat("MM/dd/yyyy").parse(scanner.nextLine());
                Date today = new Date();
                if(checkInDate.before(today)){
                    System.out.println("check in date should not be before today");
                }else {
                    break;
                }
            } catch (ParseException e) {
                System.out.println("Please enter valid date");
            }
        } while (true);
        do {
            try {
                System.out.println("Enter check out date mm/dd/yyyy example 08/16/2023");
                checkOutDate = new SimpleDateFormat("MM/dd/yyyy").parse(scanner.nextLine());
                if(!checkOutDate.after(checkInDate)){
                    System.out.println("check out date should be after check in date");
                }else {
                    break;
                }
            } catch (ParseException e) {
                System.out.println("Please enter valid date");
            }
        } while (true);

//        if no available room, find recommend room and set them as available
        Collection<IRoom> availableRooms = HotelResource.hotelResource.findARoom(checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkInDate);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            checkInDate = calendar.getTime();
            calendar.setTime(checkOutDate);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            checkOutDate = calendar.getTime();
            availableRooms = HotelResource.hotelResource.findARoom(checkInDate, checkOutDate);
            if (availableRooms.isEmpty()) {
                System.out.println("No available room and no recommended room.");
                exit = true;
            } else {
                System.out.println("No available room. See our recommendation:");
            }
        }
//        show available rooms
        if(!exit){
            System.out.println("Rooms available in " + dateFormat.format(checkInDate) + " - " + dateFormat.format(checkOutDate));
            System.out.println("----------------------------------------------------------");
            for (IRoom room : availableRooms) {
                System.out.println(room);
            }
            System.out.println("----------------------------------------------------------");
            loop: while(!exit){
                System.out.println("Would you like to book a room?(y/n)" );
                String selection = scanner.nextLine();
                switch (selection){
                    case "y" ->{
                        break loop;
                    }
                    case "n" ->{
                        exit = true;
                    }
                    default -> System.out.println("Please enter y or n" );
                }
            }
        }
//        book a room
        loop: while(!exit){
            System.out.println("Do you have an account(y/n)" );
            String selection = scanner.nextLine();
            switch (selection){
                case "y" ->{
                    break loop;
                }
                case "n" ->{
                    createAccount();
                    break loop;
                }
                default -> System.out.println("Please enter y or n" );
            }
        }
        while(!exit){
            System.out.println("Please enter your email to book a room:" );
            email = scanner.nextLine();
            Customer customer = HotelResource.hotelResource.getCustomer(email);
            if (customer == null){
                System.out.println("Can't find your account." );
            }else {
                System.out.println("Hello! " + customer.getName() );
                break;
            }
        }
        while(!exit){
            System.out.println("Please enter the room number you want to reserve:" );
            String roomNumber = scanner.nextLine();
            assert availableRooms != null;
            IRoom room = HotelResource.hotelResource.getRoom(roomNumber);
            if (availableRooms.contains(room)){
                Reservation reservation = HotelResource.hotelResource.bookARoom(email,room,checkInDate,checkOutDate);
                System.out.println("Your reservation:" );
                System.out.println("----------------------------------------------");
                System.out.println(reservation );
                System.out.println("----------------------------------------------");
                System.out.println("Thank you for your reservation!" );
                break;
            }else {
                System.out.println("This room is not available in " + dateFormat.format(checkInDate) + " - " + dateFormat.format(checkOutDate) );
            }
        }
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }

    public static void createAccount(){
        String email = null;
        do{
            System.out.println("Please enter your email:");
            email = scanner.nextLine();
            RegExTester RegExTester = new RegExTester();
            if (!RegExTester.emailTester(email)){
                System.out.println("Your input is not valid.");
                continue;
            }
            if (AdminResource.adminResource.getCustomer(email) != null) {
                System.out.println("This email already exists");
                continue;
            }
            break;
        }while (true);
        System.out.println("Please enter your First name:");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your Last name:");
        String lastName = scanner.nextLine();
        Customer customer = HotelResource.hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("You successfully create a customer:");
        System.out.println("----------------------------------------------");
        System.out.println(customer);
        System.out.println("----------------------------------------------");
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }

    public static void seeMyReservations(){
        String email = null;
        do{
            System.out.println("Please enter your email:");
            email = scanner.nextLine();
            if (AdminResource.adminResource.getCustomer(email) == null) {
                System.out.println("This email does not exist.");
            }else {
                break;
            }
        }while (true);

        Collection<Reservation> customerReservations = HotelResource.hotelResource.getCustomersReservations(email);
        if (customerReservations == null){
            System.out.println("No reservation yet.");
        }else {
            System.out.println("Reservations of " + email);
            System.out.println("-------------------------------------\n");
            for (Reservation reservation : customerReservations) {
                System.out.println(reservation);
            }
            System.out.println("\n-------------------------------------");
        }
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }
}



