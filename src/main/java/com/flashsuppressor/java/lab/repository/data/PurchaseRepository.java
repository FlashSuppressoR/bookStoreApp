package com.flashsuppressor.java.lab.repository.data;

import com.flashsuppressor.java.lab.entity.Purchase;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

}
