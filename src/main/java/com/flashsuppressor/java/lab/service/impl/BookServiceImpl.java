package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.service.dto.BookDTO;
import com.flashsuppressor.java.lab.service.dto.GenreDTO;
import com.flashsuppressor.java.lab.service.dto.PublisherDTO;
import com.flashsuppressor.java.lab.repository.data.BookRepository;
import com.flashsuppressor.java.lab.service.BookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final ModelMapper modelMapper;
    private final Pageable bookPageable = PageRequest.of(1, 5, Sort.by("name"));

    @Override
    public BookDTO findById(Long id) {
        return convertToBookDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<BookDTO> findAll(Pageable pgb) {
        Page<Book> pages = repository.findAll(bookPageable);

        return new PageImpl<>(pages.stream().map(this::convertToBookDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        Book newBook = repository.save(convertToBook(bookDTO));
        return convertToBookDTO(newBook);
    }

    @Override
    @Transactional
    public List<BookDTO> createAll(List<BookDTO> books) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookDTO newBookDTO : books) {
            Book newBook = repository.saveAndFlush(convertToBook(newBookDTO));
            bookDTOList.add(convertToBookDTO(newBook));
        }
        return bookDTOList;
    }

    @Override
    @Transactional
    public BookDTO update(BookDTO bookDTO) {
        BookDTO newBookDTO = null;
        try {
            Book book = repository.getById(bookDTO.getId());
            book.setName(bookDTO.getName());
            book.setPrice(bookDTO.getPrice());
            book.setPublisher(convertToPublisher(bookDTO.getPublisherDTO()));
            book.setGenre(convertToGenre(bookDTO.getGenreDTO()));
            book.setAmount(bookDTO.getAmount());

            repository.flush();
            newBookDTO = convertToBookDTO(book);
        }
        catch (Exception e){
            System.out.println("Can't update bookDTO");
        }
        return newBookDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
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
