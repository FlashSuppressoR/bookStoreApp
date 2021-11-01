package com.flashsuppressor.java.lab.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cart", schema = "book_store")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_counter")
    private Integer bookCounter;

    public Cart() {
    }

    public Cart(Integer id, Customer customer, Long bookId, Integer bookCounter) {
        this.id = id;
        this.customer = customer;
        this.bookId = bookId;
        this.bookCounter = bookCounter;
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getBookCounter() {
        return bookCounter;
    }

    public void setBookCounter(Integer bookCounter) {
        this.bookCounter = bookCounter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, bookId, bookCounter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cart cart = (Cart) obj;
        return id == cart.id && customer == cart.customer &&
                bookId == cart.bookId && bookCounter == cart.bookCounter;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customerId=" + customer +
                ", bookId=" + bookId +
                ", bookCounter=" + bookCounter +
                '}';
    }
}
