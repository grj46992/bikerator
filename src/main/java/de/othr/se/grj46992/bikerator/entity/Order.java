package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Order implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private Double amountOrder;
    private Date orderDate;
    private Boolean completed;
    @ManyToOne
    private Address shippingAddress;
    @ManyToOne
    private Address billingAddress;
    @OneToOne
    private Customer customer;
    @ElementCollection
    @OneToMany
    private List<Configuration> configList;

}