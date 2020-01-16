package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    public Order findByCustomer(Customer customer);
}