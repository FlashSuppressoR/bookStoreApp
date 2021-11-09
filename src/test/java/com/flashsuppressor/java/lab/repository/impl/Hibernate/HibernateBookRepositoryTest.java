package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateBookRepositoryTest extends BaseRepositoryTest {

    @Qualifier("hibernateBookRepository")
    @Autowired
    private BookRepository bookRepository;
    private final List<Book> expectedBooks = new ArrayList<>() {{
            add(new Book(1L, "Little Bee", 3.22,
                    new Publisher(1, "Big Daddy"), new Genre(1, "Fantasy"), 0));
            add(new Book(2L, "Big system Black Sun", 2.33,
                    new Publisher(2, "Minsk prod"), new Genre(1, "Fantasy"), 0));
            add(new Book(3L, "Alex Green", 13.22,
                    new Publisher(1, "New Town"), new Genre(3, "Humor"), 0));
        }};

    @Test
    public void findByIdTest() throws RepositoryException {
        Book expectedBook = new Book(1L, "Little Bee", 3.22,
                new Publisher(1, "Big Daddy"), new Genre(1, "Fantasy"), 0);
        Book actualBook = bookRepository.findById(1L);

        assertBookEquals(expectedBook, actualBook);
    }

    @Test
    public void findAllTest() throws RepositoryException{
        List<Book> actualBooks = bookRepository.findAll();
        for (int i = 0; i < expectedBooks.size(); i++) {
            assertBookEquals(expectedBooks.get(i), actualBooks.get(i));
        }
    }

    @Test
    public void createTest() throws RepositoryException {
        Book expectedBook = new Book(4L, "My mind", 132.22,
                new Publisher(4, "Boss ex"), new Genre(4, "Publish"), 0);
        bookRepository.create(expectedBook);

        assertEquals(4, bookRepository.findAll().size());
    }

    @Test
    public void createAllTest() throws RepositoryException{
        List<Book> expectedList = new ArrayList<>() {{
            add(new Book(4L, "My mind", 132.22,
                    new Publisher(4, "Boss ex"), new Genre(4, "Publish"), 0));
            add(new Book(5L, "My World", 32.32,
                    new Publisher(4, "World Wild"), new Genre(5, "History"), 0));
        }};
        List<Book> actualList = new ArrayList<>() {{
            add(new Book(null, "My mind", 132.22,
                    new Publisher(4, "Boss ex"), new Genre(4, "Publish"), 0));
            add(new Book(null, "My World", 32.32,
                    new Publisher(4, "World Wild"), new Genre(5, "History"), 0));
        }};
        bookRepository.createAll(actualList);

        for (int i = 0; i < expectedList.size(); i++) {
            assertBookEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void updateTest() throws RepositoryException {
        Book expectedBook = new Book(1L, "Dark Night", 3.22,
                new Publisher(1, "Big Daddy"), new Genre(1, "Fantasy"), 0);
        Book actualBook = bookRepository.update(expectedBook);

        assertBookEquals(expectedBook, actualBook);
    }

    @Test
    public void deleteByIdTest() throws RepositoryException {
        Long bookId = 1L;

        assertTrue(bookRepository.deleteById(bookId));
    }

    private void assertBookEquals(Book expectedBook, Book actualBook) {
        assertEquals(expectedBook.getId(), actualBook.getId());
        assertEquals(expectedBook.getName(), actualBook.getName());
        assertEquals(expectedBook.getPrice(), actualBook.getPrice());
        assertEquals(expectedBook.getPublisher(), actualBook.getPublisher());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getAmount(), actualBook.getAmount());
    }
}