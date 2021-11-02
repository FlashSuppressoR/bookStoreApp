package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernatePurchaseRepositoryTest extends BaseRepositoryTest {
    private final PurchaseRepository purchaseRepository;
    private final List<Purchase> expectedPurchases;

    public HibernatePurchaseRepositoryTest() {
        super();
        this.purchaseRepository = new HibernatePurchaseRepository(getSessionFactory().openSession());
        expectedPurchases = new ArrayList<>() {{
            add(new Purchase(1, new Customer(2, "Alex", "Alex@com", "alex"),
                    Timestamp.valueOf("2007-09-10 00:00:00.0")));
            add(new Purchase(2, new Customer(3, "Rus", "Rus@com", "rus"),
                    Timestamp.valueOf("2007-09-10 00:00:00.0")));
            add(new Purchase(3, new Customer(1, "Max", "Max@com", "max"),
                    Timestamp.valueOf("2007-09-10 00:00:00.0")));
        }};
    }

    @Test
    public void findAll() throws SQLException {
        //given
        //when
        List<Purchase> actualPurchases = purchaseRepository.findAll();
        //then
        for (int i = 0; i < expectedPurchases.size(); i++) {
            assertPurchaseEquals(expectedPurchases.get(i), actualPurchases.get(i));
        }
    }

    @Test
    public void findById() throws SQLException {
        //given
        Purchase expected = expectedPurchases.get(0);
        //when
        Purchase actual = purchaseRepository.findById(expected.getId());
        //then
        assertPurchaseEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        //given
        Purchase expectedPurchase = new Purchase(4, new Customer(4, "Alex3", "Alexw3@com", "alex33"), Timestamp.valueOf("2007-09-10 00:00:00.0"));
        //when
        Purchase actualPurchase = purchaseRepository.create(expectedPurchase);
        //then
        assertPurchaseEquals(expectedPurchase, actualPurchase);
    }

    @Test
    public void addAll() throws SQLException {
        //given
        List<Purchase> expectedList = new ArrayList<>() {{
            add(new Purchase(4, new Customer(4, "Alex3", "Alexw3@com", "alex33"), Timestamp.valueOf("2007-09-10 00:00:00.0")));
            add(new Purchase(5, new Customer(5, "Vell", "Vell@com", "vell"), Timestamp.valueOf("2007-09-10 00:00:00.0")));
        }};
        //when
        List<Purchase> actualList = new ArrayList<>() {{
            add(new Purchase(null, new Customer(4, "Alex3", "Alexw3@com", "alex33"), Timestamp.valueOf("2007-09-10 00:00:00.0")));
            add(new Purchase(null, new Customer(5, "Vell", "Vell@com", "vell"), Timestamp.valueOf("2007-09-10 00:00:00.0")));
        }};
        purchaseRepository.createAll(actualList);
        //then
        for (int i = 0; i < expectedList.size(); i++) {
            assertPurchaseEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void deleteById() throws SQLException {
        //when
        int purchaseId = 1;
        //then
        assertTrue(purchaseRepository.deleteById(purchaseId));
    }

    private void assertPurchaseEquals(Purchase expectedPurchase, Purchase actualPurchase) {
        assertEquals(expectedPurchase.getId(), actualPurchase.getId());
        assertEquals(expectedPurchase.getCustomer(), actualPurchase.getCustomer());
        assertEquals(expectedPurchase.getPurchaseTime(), actualPurchase.getPurchaseTime());
    }
}