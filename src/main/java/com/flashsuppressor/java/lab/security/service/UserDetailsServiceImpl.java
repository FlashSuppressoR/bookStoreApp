package com.flashsuppressor.java.lab.security.service;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.data.CustomerRepository;
import com.flashsuppressor.java.lab.security.entity.SecurityCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public UserDetailsServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer user = customerRepository.findCustomerByEmail(email);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User with email %s is not found", email));
        }
        return SecurityCustomer.fromCustomer(user);
    }
}
