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
    private String name;
    private String description;
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

    public Item(){
    }

    public Item(String name, String description, Double price, Double weight, Category category, ItemPool itemPool) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.category = category;
        this.itemPool = itemPool;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public ItemPool getItemPool() {
        return itemPool;
    }

    public void setItemPool(ItemPool itemPool) {
        this.itemPool = itemPool;
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