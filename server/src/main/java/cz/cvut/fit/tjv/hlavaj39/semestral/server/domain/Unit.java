package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class Unit {
    @Id
    private Integer number;
    private String name;
    private String location;
    @JsonIgnore
    @OneToMany(mappedBy = "unit")
    private Set<Scout> members;

    public Unit(){
    }

    public Unit(Integer number, String name, String location, Set<Scout> members) {
        this.number = number;
        this.name = name;
        this.location = location;
        this.members = members;
    }

    public Unit(Integer number) {
        this.number = number;
        this.name = null;
        this.location = null;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Scout> getMembers() {
        return members;
    }

    public void setMembers(Set<Scout> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(number, unit.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

}
