package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.List;


public interface CustomerService {

    Customer createNewCustomer(Customer customer);

    Customer getCustomerById(long id);

    List<Customer> getAllCustomers();

    Customer getOwnerByPetId(Long petId);
}
