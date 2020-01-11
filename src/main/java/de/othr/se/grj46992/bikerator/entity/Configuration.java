package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Embeddable
public class Configuration implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long configurationId;
    private String name;
    private String description;
    private Date createDate;
    private Double amountTotal;
    @ManyToMany
    @JoinTable(name = "configuration_items",
            joinColumns = {@JoinColumn(name = "configurationId")},
            inverseJoinColumns = {@JoinColumn(name = "itemId")})
    private List<Item> itemList;

    public Configuration () {
        this.itemList = new ArrayList<Item>();
        this.amountTotal = 0.0;
    }

    public Long getConfigurationId() {
        return configurationId;
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

    public void removeItem(Item item) {
        this.itemList.remove(item);
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Configuration c = (Configuration) obj;
        if (c.getConfigurationId() == this.getConfigurationId()) {
            return true;
        }
        return false;
    }
}