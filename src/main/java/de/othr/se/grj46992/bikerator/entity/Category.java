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
    @OneToMany(mappedBy = "category")
    private List<Item> itemList;
    @ManyToOne
    private Category fatherCategory;
    @OneToMany(mappedBy = "fatherCategory")
    private List<Category> childCategories;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Category getFatherCategory() {
        return fatherCategory;
    }

    public boolean isFather(){
        if (this.childCategories.isEmpty()) {
            return false;
        }
        else return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Category c = (Category) obj;
        if (c.getCategoryId() == this.getCategoryId()) {
            return true;
        }
        return false;
    }

    public void setFatherCategory(Category fatherCategory) {
        this.fatherCategory = fatherCategory;
    }

    public List<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<Category> childCategories) {
        this.childCategories = childCategories;
    }

    public void addChildCategory(Category childCategory) {
        this.childCategories.add(childCategory);
    }
}