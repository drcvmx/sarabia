package com.vs.customer.service;

import com.vs.customer.dto.CustomerDTO;
import com.vs.customer.dto.CustomerDetailsDTO;
import com.vs.customer.entity.Customer;
import com.vs.customer.exception.CustomerNotFoundException;
import com.vs.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class CustomerService {
    private final Logger logger = Logger.getLogger(CustomerService.class.getName());

    @Autowired
    private CustomerRepository repository;

    public CustomerDTO getCustomerById(String id) {
        logger.log(Level.INFO, "Buscando cliente por ID: {0}", id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return convertToDTO(customer);
    }

    public CustomerDetailsDTO getCustomerDetails(String id) {
        logger.log(Level.INFO, "Obteniendo detalles del cliente: {0}", id);
        CustomerDTO customerDTO = getCustomerById(id);
        CustomerDetailsDTO details = new CustomerDetailsDTO();
        details.setId(customerDTO.getId());
        details.setCustomerName(customerDTO.getCustomerName());
        return details;
    }

    public CustomerDTO saveCustomer(@Valid CustomerDTO customerDTO) {
        logger.log(Level.INFO, "Guardando cliente: {0}", customerDTO.getId());
        try {
            Customer customer = convertToEntity(customerDTO);
            customer = repository.save(customer);
            logger.log(Level.INFO, "Cliente guardado exitosamente: {0}", customer.getId());
            return convertToDTO(customer);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar cliente: " + customerDTO.getId(), e);
            throw new RuntimeException("Error al guardar el cliente: " + e.getMessage());
        }
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setCustomerName(customer.getCustomerName());
        return dto;
    }

    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setCustomerName(dto.getCustomerName());
        return customer;
    }
}
