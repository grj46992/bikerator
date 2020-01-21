package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Embeddable
public class ItemPool extends LongIdEntity {
    @ManyToMany(mappedBy = "itemPoolList")
    private List<Item> itemList;

    public ItemPool() {
        this.itemList = new ArrayList<Item>();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void addItem(Item item) {
        this.itemList.add(item);
    }

}
