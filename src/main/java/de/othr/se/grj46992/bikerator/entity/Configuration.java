package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Embeddable
public class Configuration extends LongIdEntity {
    private String name;
    private String description;
    private Date createDate;
    private Double amountTotal = 0.0;
    @ManyToMany
    @JoinTable(name = "configuration_items",
            joinColumns = {@JoinColumn(name = "configurationId")},
            inverseJoinColumns = {@JoinColumn(name = "itemId")})
    private List<Item> itemList = new ArrayList<Item>();

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void addItem(Item item) {
        this.itemList.add(item);
    }
}