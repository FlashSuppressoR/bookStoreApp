package com.flashsuppressor.java.lab.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "review", schema = "book_store")
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "mark")
    private Integer mark;
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Review() {
    }

    public Review(Integer id, Integer mark, String comment, Book book) {
        this.id = id;
        this.mark = mark;
        this.comment = comment;
        this.book = book;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, comment, book);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Review review = (Review) obj;
        return id == review.id && mark == review.mark & comment.equals(review.comment) && book.equals(review.book);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", mark=" + mark +
                ", comment='" + comment + '\'' +
                ", bookId=" + book +
                '}';
    }
}
