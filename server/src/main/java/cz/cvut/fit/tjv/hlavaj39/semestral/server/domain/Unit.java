package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import java.util.Objects;
import java.util.Set;

public class Unit {
    private int number;
    private String name;
    private String location;
    private Set<Scout> members;


    public Unit(int number, String name, String location, Set<Scout> members) {
        this.number = number;
        this.name = name;
        this.location = location;
        this.members = members;
    }

    public Unit(int number) {
        this.number = number;
        this.name = null;
        this.location = null;
        this.members = null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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
        return number == unit.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}