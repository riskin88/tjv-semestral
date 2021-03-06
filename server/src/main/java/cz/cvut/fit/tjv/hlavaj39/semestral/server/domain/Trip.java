package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String destination;
   @JsonIgnore
    @ManyToMany(mappedBy = "trips")
    private Set<Scout> participants;

    public Trip(){
    }

    public Trip(Integer id, String destination, Set<Scout> participants) {
        this.id = id;
        this.destination = destination;
        this.participants = participants;
    }

    public Trip(Integer id) {
        this.id = id;
        this.destination = null;
        this.participants = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Set<Scout> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Scout> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
