package com.flashsuppressor.java.lab.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@Table(name = "purchase", schema = "book_store")
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "purchase_time")
    private Timestamp purchaseTime;

    public Purchase() {
    }

    public Purchase(Integer id, Customer customer, Timestamp purchaseTime) {
        this.id = id;
        this.customer = customer;
        this.purchaseTime = purchaseTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Timestamp getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, purchaseTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Purchase purchase = (Purchase) obj;
        return id == purchase.id && customer.equals(purchase.customer) && purchaseTime.equals(purchase.purchaseTime);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", customerId=" + customer +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}
