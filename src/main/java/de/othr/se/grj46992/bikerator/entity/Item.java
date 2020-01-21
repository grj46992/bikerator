package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Embeddable
public class Item extends LongIdEntity {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Double price;
    @NotNull
    private Double weight;
    @NotNull
    private String imagePath;
    @ManyToOne
    private Category category;
    @ManyToMany
    private List<ItemPool> itemPoolList;
    @OneToMany(mappedBy = "item")
    private List<DepotItem> depotItemList;

    public Item(){
    }

    public Item(String name, String description, Double price, Double weight, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.category = category;
        this.itemPoolList = new ArrayList<ItemPool>();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ItemPool> getItemPoolList() {
        return itemPoolList;
    }

    public void addItemPool(ItemPool itemPool){ this.itemPoolList.add(itemPool); }

    public void setItemPoolList(List<ItemPool> itemPoolList) {
        this.itemPoolList = itemPoolList;
    }

    public List<DepotItem> getDepotItemList() {
        return depotItemList;
    }

    public void setDepotItemList(List<DepotItem> depotItemList) {
        this.depotItemList = depotItemList;
    }

    public int getStock() {
        return this.depotItemList.size();
    }


}