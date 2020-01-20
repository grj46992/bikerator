package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;
import de.othr.se.grj46992.bikerator.repository.AddressRepository;
import de.othr.se.grj46992.bikerator.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public void updateCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        addressRepository.save(customer.getAddress());
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        Long addressId = customer.getAddress().getId();
        customerRepository.deleteById(customer.getUsername());
        addressRepository.deleteById(addressId);
    }

    @Override
    public Iterable<Customer> readAllCustomers() {
        Iterable<Customer> allCustomers = customerRepository.findAll();
        return allCustomers;
    }

    @Override
    public List<String> readCustomerEmailList() {
        List<String> customerEmailList = new ArrayList<String>();
        Iterable<Customer> allCustomers = customerRepository.findAll();
        for (Customer customer : allCustomers) {
            customerEmailList.add(customer.getEmail());
        }
        return customerEmailList;
    }

    @Override
    public Customer readById(String username) {
        Optional<Customer> optional = customerRepository.findById(username);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            return customer;
        }
        return null;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void updateCustomerConfigurationList(Customer user, Configuration configuration) {
        user.addConfiguration(configuration);
        customerRepository.save(user);
    }

    @Override
    public void createPost(String title, String text, String email, String password, String picture) {
        // TODO create post object and send to friendzone
    }

    @Override
    public void updateCompletedOrderList(Customer customer, Order order) {
        customer.addOrderToCompletedOrderList(order);
        customerRepository.save(customer);
    }

    @Override
    public void deleteCurrentOrder(Customer customer) {
        customer.setCurrentOrder(null);
        customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> optional = customerRepository.findById(username);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            return customer;
        } else {
            throw new UsernameNotFoundException("Kunde mit Namen " + username + " existiert nicht");
        }
    }
}
