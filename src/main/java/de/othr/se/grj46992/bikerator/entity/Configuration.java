package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Configuration implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long configurationId;
    private String name;
    private String description;
    private Date createDate;
    private Double amountTotal;
    @ElementCollection
    @OneToMany
    private List<DepotItem> itemList;

}