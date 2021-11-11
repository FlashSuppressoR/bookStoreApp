package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.repository.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.*;
import org.springframework.test.context.TestPropertySource;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@ComponentScan("com.flashsuppressor.java.lab.service")
@Profile("test")
@TestPropertySource(locations = "classpath:/application-test.properties",
        properties = "baeldung.testpropertysource.one=other-property-value")
public class TestServiceConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
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
