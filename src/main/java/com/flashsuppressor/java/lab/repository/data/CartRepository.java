package com.flashsuppressor.java.lab.repository.data;

import com.flashsuppressor.java.lab.entity.Cart;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
