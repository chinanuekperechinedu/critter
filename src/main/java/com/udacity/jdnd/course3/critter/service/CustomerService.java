package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CustomerService {

    Customer createNewCustomer(Customer customer);

    Customer getCustomerById(long id);

    List<Customer> getAllCustomers();

    Customer getOwnerByPetId(Long petId);
}
