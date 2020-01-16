package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="`Order`")
public class Order implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private Double amountOrder = 0.0;
    private Date orderDate;
    private Boolean completed = false;
    @ManyToOne
    private Address shippingAddress;
    @ManyToOne
    private Address billingAddress;
    @OneToOne
    private Customer customer;
    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Configuration> configList = new ArrayList<Configuration>();

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getAmountOrder() {
        return amountOrder;
    }

    public void setAmountOrder(Double amountOrder) {
        this.amountOrder = amountOrder;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Configuration> getConfigList() {
        return configList;
    }

    public void addConfiguration(Configuration configuration) {
        this.configList.add(configuration);
    }
}