package model;

import java.util.HashMap;
import java.util.HashSet;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;
    public static HashMap<String, Customer> allCustomers = new HashMap<>();
    public Customer ( String email, String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        RegExTester RegExTester = new RegExTester();
        if (RegExTester.emailTester(email)){
            this.email = email;
        } else{
            throw new IllegalArgumentException();
        }
    }
    public String getEmail(){
        return email;
    }
    public String getName(){
        return firstName + " " + lastName;
    }
    @Override
    public String toString() {
        return "Email: " + email + "  Name: " + firstName + " " + lastName;
    }

}
