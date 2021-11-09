package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.CustomerRepository;
import com.flashsuppressor.java.lab.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(@Qualifier("hibernateCustomerRepository")
                                   CustomerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO findByEmail(String email) throws ServiceException {
        CustomerDTO customerDTO = null;
        try{
            Customer customer = repository.findByEmail("Max@com");
            customerDTO = convertToCustomerDTO(customer);
        }
        catch (RepositoryException ex){
            throw new ServiceException(ex.getMessage());
        }
        return customerDTO;
    }

    @Override
    public CustomerDTO findById(int id) throws ServiceException {
        CustomerDTO customerDTO = null;
        try{
            Customer customer = repository.findById(1);
            customerDTO = convertToCustomerDTO(customer);
        }
        catch (RepositoryException ex){
            throw new ServiceException(ex.getMessage());
        }
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> findAll() throws ServiceException{
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        List<Customer> customers = null;
        try {
            customers = repository.findAll();
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        if (customers != null && customers.size() > 0) {
            customerDTOs = customers.stream().map(this::convertToCustomerDTO).collect(Collectors.toList());
        }
        return customerDTOs;
    }

    @Override
    public void create(Customer customer) throws ServiceException {
        try {
            repository.create(customer);
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public CustomerDTO update(Customer customer) throws ServiceException {
        CustomerDTO updatedCustomerDTO = null;
        try {
            Customer updatedCustomer = repository.update(customer);
            if (updatedCustomer != null) {
                updatedCustomerDTO = convertToCustomerDTO(updatedCustomer);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        return updatedCustomerDTO;
    }

    @Override
    public boolean deleteById(int id) throws ServiceException {
        boolean result;
        try {
            result = repository.deleteById(id);
        } catch (RepositoryException | SQLException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return result;
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

}
