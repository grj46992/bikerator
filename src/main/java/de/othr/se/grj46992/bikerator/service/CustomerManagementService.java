package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.repository.AddressRepository;
import de.othr.se.grj46992.bikerator.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("labresources")
public class CustomerManagementService implements CustomerManagementServiceIF, UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        addressRepository.save(customer.getAddress());
        customerRepository.save(customer);
    }

    @Override
    public Iterable<Customer> findAllCustomers() {
        Iterable<Customer> allCustomers = customerRepository.findAll();
        return allCustomers;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("Kunde mit Namen " + username + " existiert nicht" );
        } else {
            return customer;
        }
    }

    @Override
    public Customer findByUsername(String username) {
        Customer user = customerRepository.findByUsername(username);
        return user;
    }

    @Override
    public void updateCustomerConfigurationList(Customer user, Configuration configuration) {
        user.addConfiguration(configuration);
        customerRepository.save(user);
    }
}
