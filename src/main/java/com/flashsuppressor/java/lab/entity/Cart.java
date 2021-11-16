package com.flashsuppressor.java.lab.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Data
@Table(name = "cart", schema = "book_store")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_counter")
    private Integer bookCounter;

    public Cart(){
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
}
