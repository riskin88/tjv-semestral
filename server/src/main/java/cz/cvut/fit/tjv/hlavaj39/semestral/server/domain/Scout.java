package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller.Views;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class Scout {
    @JsonView(Views.Brief.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonView(Views.All.class)
    @Column(nullable = false)
    private String name;
    @JsonView(Views.All.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d.M.yyyy")
    private LocalDate dateOfBirth;
    @JsonView(Views.All.class)
    @ManyToOne
    private Unit unit;
    @JsonIgnore
    @ManyToMany
    private Set<Trip> trips;

    public Scout(){
    }

    public Scout(int id, String name, LocalDate dateOfBirth, Unit unit, Set<Trip> trips) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.unit = unit;
        this.trips = trips;
    }

    public Scout(int id) {
        this.id = id;
        this.name = null;
        this.dateOfBirth = null;
        this.unit = null;
        this.trips = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scout scout = (Scout) o;
        return id == scout.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
