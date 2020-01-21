package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@MappedSuperclass
public class StringIdEntity {
    @Id
    @NotNull
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        StringIdEntity other = (StringIdEntity) o;
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
