package com.vs.customer.controller;

import com.vs.customer.dto.CustomerDTO;
import com.vs.customer.dto.CustomerDetailsDTO;
import com.vs.customer.exception.CustomerNotFoundException;
import com.vs.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/customers")
@CrossOrigin
@Validated
public class CustomerController {
    private final Logger logger = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customer) {
        logger.log(Level.INFO, "Creando nuevo cliente: {0}", customer.getId());
        CustomerDTO savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailsDTO> getCustomer(@PathVariable("id") String id) {
        logger.log(Level.INFO, "Buscando cliente con ID: {0}", id);
        try {
            CustomerDetailsDTO customer = customerService.getCustomerDetails(id);
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            logger.log(Level.WARNING, "Cliente no encontrado: {0}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar cliente: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
