package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.repository.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("com.flashsuppressor.java.lab.service")
public class TestServiceConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    @Primary
    public AuthorRepository hibernateAuthorRepository(){
        return Mockito.mock(AuthorRepository.class);
    }

    @Bean
    @Primary
    public BookRepository bookRepository(){
        return Mockito.mock(BookRepository.class);
    }

    @Bean
    @Primary
    public CartRepository cartRepository(){
        return Mockito.mock(CartRepository.class);
    }

    @Bean
    @Primary
    public CustomerRepository hibernateCustomerRepository(){
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    @Primary
    public GenreRepository hibernateGenreRepository(){
        return Mockito.mock(GenreRepository.class);
    }

    @Bean
    @Primary
    public PurchaseRepository purchaseRepository(){
        return Mockito.mock(PurchaseRepository.class);
    }

    @Bean
    @Primary
    public PublisherRepository publisherRepository(){
        return Mockito.mock(PublisherRepository.class);
    }

    @Bean
    @Primary
    public ReviewRepository reviewRepository(){
        return Mockito.mock(ReviewRepository.class);
    }
}
