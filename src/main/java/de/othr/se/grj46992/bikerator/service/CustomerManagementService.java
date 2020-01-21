package de.othr.se.grj46992.bikerator.service;

import de.alexanderhamedinger.friendzone.entities.Post;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("labresources")
@Transactional
public class CustomerManagementService implements CustomerManagementServiceIF, UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restServiceClient;

    @Override
    public int createCustomer(Customer newCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(newCustomer.getUsername());
        if (optionalCustomer.isPresent()) {
            // Username already exists
            return 0;
        } else {
            Customer existingCustomer = customerRepository.findByEmail(newCustomer.getEmail());
            if (existingCustomer != null) {
                // Email address already exists
                return 1;
            } else {
                // Create new customer
                newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
                addressRepository.save(newCustomer.getAddress());
                customerRepository.save(newCustomer);
                return 2;
            }
        }
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
    public List<String> readAllCustomerEmails() {
        List<String> emailList = new ArrayList<String>();
        Iterable<Customer> allCustomers = customerRepository.findAll();
        for (Customer customer : allCustomers) {
            emailList.add(customer.getEmail());
        }
        return emailList;
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
    public void createPost(String title, String email) throws RestClientException {
        // Create new post and set customer email and title
        Post post = new Post();
        post.setTitle("Ich habe mit einen neuen Drahtesel auf Bikerator erstellt! Name: " + title + "§" + email);
        post.setUser(null);
        post.setCreationDate(new GregorianCalendar());
        // Send post to friendzone
        restServiceClient.postForObject("http://im-codd:8861/restapi/posts", post, Post.class);
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

    // Necessary for spring security
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
