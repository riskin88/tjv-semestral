package cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.EntityStateException;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.TripService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class TripController {
    private final TripService tripService;

    TripController(TripService tripService) {this.tripService = tripService;}

    @PostMapping("/trips")
    Trip newTrip (@RequestBody Trip trip){
        if (trip.getId() != null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot choose ID");
        try{
            return tripService.create(trip);
        }
        catch (EntityStateException e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Trip creation failed");
        }
    }

    @GetMapping("/trips")
    Collection<Trip> all(){
        return tripService.readAll();
    }

    @GetMapping("/trips/{id}")
    Trip one(@PathVariable int id){
        return tripService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trip Not Found")
        );
    }

    @JsonView(Views.Brief.class)
    @GetMapping("/trips/{id}/scouts")
    Collection<Scout> participants(@PathVariable int id){
        tripService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trip Not Found"));
        return tripService.readScoutsById(id);
    }

    @PutMapping("/trips/{id}")
    Trip updateTrip(@RequestBody Trip trip, @PathVariable int id){
        tripService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trip Not Found")
        );
        if (trip.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Forced ID");
        }
        trip.setId(id);
        return tripService.update(trip);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("trips/{id}")
    void deleteTrip(@PathVariable int id){
        tripService.deleteById(id);
    }
}
