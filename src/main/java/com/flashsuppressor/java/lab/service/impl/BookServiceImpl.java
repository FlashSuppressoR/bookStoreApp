package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.entity.dto.BookDTO;
import com.flashsuppressor.java.lab.entity.dto.GenreDTO;
import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.repository.BookRepository;
import com.flashsuppressor.java.lab.service.BookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly=true)
    public BookDTO findById(Long id) {
        return convertToBookDTO(repository.findById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public List<BookDTO> findAll() {
        return repository.findAll().stream().map(this::convertToBookDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        Book newBook = repository.create(convertToBook(bookDTO));
        return convertToBookDTO(newBook);
    }

    @Override
    @Transactional
    public List<BookDTO> createAll(List<BookDTO> books) {
        List<BookDTO> bookDTOList = null;
        for (BookDTO newBookDTO : books) {
            Book newBook = repository.create(convertToBook(newBookDTO));
            bookDTOList.add(convertToBookDTO(newBook));
        }
        return bookDTOList;
    }

    @Override
    @Transactional
    public BookDTO update(BookDTO bookDTO) {
        BookDTO newBookDTO = null;
        try {
            Book book = repository.findById(bookDTO.getId());
            if (bookDTO.getName() != null) {
                book.setName(bookDTO.getName());
            }
            book.setPrice(bookDTO.getPrice());
            if (bookDTO.getPublisherDTO() != null) {
                book.setPublisher(convertToPublisher(bookDTO.getPublisherDTO()));
            }
            if (bookDTO.getGenreDTO() != null) {
                book.setGenre(convertToGenre(bookDTO.getGenreDTO()));
            }
            book.setAmount(bookDTO.getAmount());
            newBookDTO = convertToBookDTO(book);
        }
        catch (Exception e){
            System.out.println("Can't update bookDTO");
        }
        return newBookDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {

        return repository.deleteById(id);
    }

    private Genre convertToGenre(GenreDTO genreDTO) {
        return modelMapper.map(genreDTO, Genre.class);
    }

    private Publisher convertToPublisher(PublisherDTO publisherDTO) {
        return modelMapper.map(publisherDTO, Publisher.class);
    }

    private Book convertToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    private BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
