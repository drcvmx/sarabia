package com.vs.customer.controller;

import com.vs.customer.dto.CustomerDTO;
import com.vs.customer.dto.CustomerDetailsDTO;
import com.vs.customer.dto.JwtTokenDTO;
import com.vs.customer.model.Customer;
import com.vs.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("/vs-customers/api")
public class CustomerController {
    final Logger logger = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    CustomerService customerService;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(@RequestBody CustomerDTO customer) throws Exception {
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(customer.getId());
        final String token = jwtTokenHelper.generateToken(userDetails);

        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
        jwtTokenDTO.setJwtToken(token);

        logger.info("Customer " + customer.getId() + " signed in....");

        return ResponseEntity.ok(jwtTokenDTO);
    }

    @PostMapping("/signup")
    public Customer signUp(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/customers/{id}")
    private CustomerDetailsDTO getCustomer(@PathVariable("id") String id) {
        CustomerDetailsDTO retVal = new CustomerDetailsDTO();
        try {
            retVal = customerService.getCustomerDetails(id);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
