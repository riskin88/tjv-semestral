package cz.cvut.fit.tjv.hlavaj39.semestral.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public class UnitDto {
    private Integer number;
    private String name;
    private String location;

    public UnitDto(){
    }

    public UnitDto(Integer number, String name, String location) {
        this.number = number;
        this.name = name;
        this.location = location;
    }

    public UnitDto(Integer number) {
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
}
