package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.entity.Role;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.hibernate.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class PurchaseRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;
    private final List<Purchase> expectedPurchases = new ArrayList<>() {{
        add(new Purchase(1, new Customer(2, "Alex", "Alex@com", "alex", Role.ADMIN),
                Timestamp.valueOf("2007-09-10 00:00:00.0")));
        add(new Purchase(2, new Customer(3, "Rus", "Rus@com", "rus", Role.USER),
                Timestamp.valueOf("2007-09-10 00:00:00.0")));
        add(new Purchase(3, new Customer(1, "Max", "Max@com", "max", Role.USER),
                Timestamp.valueOf("2007-09-10 00:00:00.0")));
    }};

    @Test
    public void findAllTest() {
        List<Purchase> actualPurchases = purchaseRepository.findAll();

        for (int i = 0; i < expectedPurchases.size(); i++) {
            assertPurchaseEquals(expectedPurchases.get(i), actualPurchases.get(i));
        }
    }

    @Test
    public void findByIdTest() {
        Purchase expected = expectedPurchases.get(0);
        Purchase actual = purchaseRepository.findById(expected.getId());

        assertPurchaseEquals(expected, actual);
    }

    @Test
    public void createTest() {
        Purchase expectedPurchase = new Purchase(4, new Customer(4, "Alex3", "Alexw3@com", "alex33", Role.USER), Timestamp.valueOf("2007-09-10 00:00:00.0"));
        purchaseRepository.create(expectedPurchase);

        assertEquals(4, purchaseRepository.findAll().size());
    }

    @Test
    public void createAllTest() {
        List<Purchase> expectedList = new ArrayList<>() {{
            add(new Purchase(4, new Customer(4, "Alex3", "Alexw3@com", "alex33", Role.USER), Timestamp.valueOf("2007-09-10 00:00:00.0")));
            add(new Purchase(5, new Customer(5, "Vell", "Vell@com", "vell", Role.USER), Timestamp.valueOf("2007-09-10 00:00:00.0")));
        }};
        List<Purchase> actualList = new ArrayList<>() {{
            add(new Purchase(null, new Customer(4, "Alex3", "Alexw3@com", "alex33", Role.USER), Timestamp.valueOf("2007-09-10 00:00:00.0")));
            add(new Purchase(null, new Customer(5, "Vell", "Vell@com", "vell", Role.USER), Timestamp.valueOf("2007-09-10 00:00:00.0")));
        }};
        purchaseRepository.createAll(actualList);

        for (int i = 0; i < expectedList.size(); i++) {
            assertPurchaseEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void deleteByIdTest() {
        int purchaseId = 1;

        assertTrue(purchaseRepository.deleteById(purchaseId));
    }

    private void assertPurchaseEquals(Purchase expectedPurchase, Purchase actualPurchase) {
        assertEquals(expectedPurchase.getId(), actualPurchase.getId());
        assertEquals(expectedPurchase.getCustomer(), actualPurchase.getCustomer());
        assertEquals(expectedPurchase.getPurchaseTime(), actualPurchase.getPurchaseTime());
    }
}