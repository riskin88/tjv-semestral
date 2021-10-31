package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import java.util.Objects;

public class Unit {
    private int number;
    private String name;
    private String location;


    public Unit(int number, String name, String location) {
        this.number = number;
        this.name = name;
        this.location = location;
    }

    public Unit(int number) {
        this.number = number;
        this.name = null;
        this.location = null;
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
