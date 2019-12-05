package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
public class Category implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    private String name;
    private String description;
    private String  imagePath;
    @ElementCollection
    @OneToMany(mappedBy = "category")
    private List<Item> itemList;
    @ManyToOne
    private Category fatherCategory;
    @ElementCollection
    @OneToMany(mappedBy = "fatherCategory")
    private List<Category> childCategories;
}