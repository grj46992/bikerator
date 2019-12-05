package de.othr.se.grj46992.bikerator.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Embeddable
public class Customer implements Serializable {
    @Id
    @NotNull
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @OneToOne
    private Address address;
    @OneToOne
    private Order currentOrder;
    @ElementCollection
    @OneToMany(mappedBy = "customer")
    private List<Order> completedOrderList;
    @ElementCollection
    @OneToMany
    private List<Configuration> configList;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
