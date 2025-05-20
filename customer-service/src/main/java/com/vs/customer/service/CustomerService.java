package com.vs.customer.service;

import com.vs.customer.dto.CustomerDTO;
import com.vs.customer.dto.CustomerDetailsDTO;
import com.vs.customer.entity.Customer;
import com.vs.customer.exception.CustomerNotFoundException;
import com.vs.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final Logger logger = Logger.getLogger(CustomerService.class.getName());

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public CustomerDTO getCustomerById(String id) {
        logger.log(Level.INFO, "Buscando cliente por ID: {0}", id);
        Customer customer = customerRepository.findById(id)
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

    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        logger.log(Level.INFO, "Guardando cliente: {0}", customerDTO.getId());
        try {
            Customer customer = new Customer();
            customer.setId(customerDTO.getId());
            customer.setCustomerDetails(objectMapper.writeValueAsString(customerDTO));
            customer = customerRepository.save(customer);
            logger.log(Level.INFO, "Cliente guardado exitosamente: {0}", customer.getId());
            return customerDTO;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar el cliente: " + e.getMessage(), e);
            throw new RuntimeException("Error al guardar el cliente", e);
        }
    }

    private CustomerDTO convertToDTO(Customer customer) {
        try {
            if (customer.getCustomerDetails() != null) {
                return objectMapper.readValue(customer.getCustomerDetails(), CustomerDTO.class);
            }
            CustomerDTO dto = new CustomerDTO();
            dto.setId(customer.getId());
            return dto;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al convertir cliente a DTO: " + e.getMessage(), e);
            throw new RuntimeException("Error al convertir cliente a DTO", e);
        }
    }
}
