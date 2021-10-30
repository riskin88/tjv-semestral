package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller.Views;

import java.time.LocalDate;
import java.util.Objects;

public class Scout {
    @JsonView(Views.Brief.class)
    private int id;
    @JsonView(Views.All.class)
    private String name;
    @JsonView(Views.All.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d.M.yyyy")
    private LocalDate dateOfBirth;

    public Scout(int id, String name, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Scout(int id) {
        this.id = id;
        this.name = null;
        this.dateOfBirth = null;
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
