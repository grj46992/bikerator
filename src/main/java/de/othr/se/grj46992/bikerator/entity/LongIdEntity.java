package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@MappedSuperclass
public class LongIdEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        LongIdEntity other = (LongIdEntity) o;
        if(!Objects.equals(id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}