package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Embeddable
public class DepotItem extends LongIdEntity {
    @ManyToOne
    private Item item;

    public DepotItem(){
    }

    public DepotItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}