package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(@Qualifier("hibernateAuthorRepository")
                                     AuthorRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AuthorDTO findById(int id) throws ServiceException {
        AuthorDTO authorDTO = null;
        try{
            Author author = repository.findById(1);
            authorDTO = convertToAuthorDTO(author);
        }
        catch (RepositoryException ex){
            throw new ServiceException(ex.getMessage());
        }
        return authorDTO;
    }

    @Override
    @Transactional
    public List<AuthorDTO> findAll() throws ServiceException{
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        List<Author> contracts = null;
        try {
            contracts = repository.findAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        if (contracts.size() > 0) {
            authorDTOs = contracts.stream().map(this::convertToAuthorDTO).collect(Collectors.toList());
        }
        return authorDTOs;
    }

    @Override
    @Transactional
    public AuthorDTO create(Author author) throws ServiceException {
        AuthorDTO newAuthorDTO = null;
        try {
            Author newAuthor = repository.create(author);
            if(newAuthor != null){
                newAuthorDTO = convertToAuthorDTO(newAuthor);
            }
            else {
                System.out.println("Object newAuthor == null");
            }

        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        return newAuthorDTO;
    }

    @Override
    @Transactional
    public void createAll(List<Author> authors) throws ServiceException{
        try {
            for (Author author : authors){
                repository.create(author);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public AuthorDTO update(Author author) throws ServiceException {
        AuthorDTO updatedAuthorDTO = null;
        try {
            Author updatedAuthor = repository.update(author);
            if (updatedAuthor != null) {
                updatedAuthorDTO = convertToAuthorDTO(updatedAuthor);
            }
        } catch (RepositoryException | SQLException ex) {
            ex.printStackTrace();
        }
        return updatedAuthorDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) throws ServiceException {
        boolean result;
        try {
            result = repository.deleteById(id);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return result;
    }

    private AuthorDTO convertToAuthorDTO(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }
}
