package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.BookRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBCBookRepositoryTest extends BaseRepositoryTest {
    private final BookRepository bookRepository;
    private final List<Book> expectedBooks;

    public JDBCBookRepositoryTest() {
        super();
        bookRepository = new JDBCBookRepository(getConnectionPool());
        expectedBooks = new ArrayList<>() {{
            add(new Book( 1L , "Little Bee", 3.22 ,
                    new Publisher(1 , "Big Daddy"), new Genre( 1 , "Fantasy"),0));
            add(new Book( 2L , "Big system Black Sun", 2.33,
                    new Publisher(2 , "Minsk prod"), new Genre( 2 , "Horror"),0));
            add(new Book( 3L , "Alex Green", 13.22,
                    new Publisher(3 , "New Town"), new Genre( 3 , "Humor"),0));
        }};
    }

    @Test
    public void findAllTest() throws SQLException {
        //given
        //when
        List<Book> actualBooks = bookRepository.findAll();
        //then
        for (int i = 0; i < expectedBooks.size(); i++) {
            assertBookEquals(expectedBooks.get(i), actualBooks.get(i));
        }
    }

    @Test
    public void updateTest() throws SQLException {
        //given
        Book expectedBook = new Book( 1L , "Dark Night", 3.22 ,
                new Publisher(1 , "Big Daddy"), new Genre( 1 , "Fantasy"),0);
        //when
        Book actualBook = bookRepository.update(expectedBook);
        //then
        assertBookEquals(expectedBook, actualBook);
    }

    @Test
    public void findBookByIdTest() throws SQLException {
        //given
        Book expectedBook = new Book( 1L , "Little Bee", 3.22 ,
                new Publisher(1 , "Big Daddy"), new Genre( 1 , "Fantasy"),0);
        //when
        Book actualBook = bookRepository.findById(1L);
        //then
        assertBookEquals(expectedBook, actualBook);
    }

    @Test
    public void deleteByIdTest() throws SQLException {
        //when
        int bookId = 1;
        //then
        assertTrue(bookRepository.deleteById(bookId));
    }

    @Test
    public void addTest() throws SQLException {
        //given
        Book expectedBook = new Book( 4L , "My mind", 132.22 ,
                new Publisher(4 , "Boss ex"), new Genre( 4 , "Publish"),0);
        //when
        Book actualBook = bookRepository.add(expectedBook);
        //then
        assertBookEquals(expectedBook, actualBook);
    }

    @Test
    public void addAllTest() throws SQLException {
        //given
        List<Book> expectedList = new ArrayList<>(){{
            add(new Book( 4L , "My mind", 132.22 ,
                    new Publisher(4 , "Boss ex"), new Genre( 4 , "Publish"),0));
            add(new Book( 5L , "My World", 32.32 ,
                    new Publisher(4 , "World Wild"), new Genre( 5 , "History"),0));
        }};
        //when
        List<Book> actualList = new ArrayList<>(){{
            add(new Book( null , "My mind", 132.22 ,
                    new Publisher(4 , "Boss ex"), new Genre( 4 , "Publish"),0));
            add(new Book( null, "My World", 32.32 ,
                    new Publisher(4 , "World Wild"), new Genre( 5 , "History"),0));
        }};
        bookRepository.addAll(actualList);
        //then
        for (int i = 0; i < expectedList.size(); i++) {
            assertBookEquals(expectedList.get(i), actualList.get(i));
        }
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