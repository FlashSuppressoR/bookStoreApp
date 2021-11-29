package com.flashsuppressor.java.lab.repository.data;

import com.flashsuppressor.java.lab.entity.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);

    Boolean deleteById(int id);
}
