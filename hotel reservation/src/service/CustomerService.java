package service;

import model.Customer;

import java.util.Collection;
import java.util.HashSet;

public class CustomerService {
    public static CustomerService customerService = new CustomerService();
    private CustomerService(){}

    public Customer addCustomer(String email, String firstName, String lastName){
        Customer newCustomer = new Customer(email, firstName, lastName);
        Customer.allCustomers.put(email, newCustomer);
        return newCustomer;
    }
    public Customer getCustomer(String customerEmail){
        return Customer.allCustomers.getOrDefault(customerEmail, null);
    }
    public Collection<Customer> getAllCustomers(){
        return Customer.allCustomers.values();
    }
}
