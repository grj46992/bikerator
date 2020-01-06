package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Embeddable
public class DepotItem implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long depotItemId;
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
}