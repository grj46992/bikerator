package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
public class Item implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    private Double price;
    private Double weight;
    private String imagePath;
    @ManyToOne
    private Category category;
    @ManyToOne
    private ItemPool itemPool;
    @ElementCollection
    @OneToMany(mappedBy = "item")
    private List<DepotItem> depotItemList;
}