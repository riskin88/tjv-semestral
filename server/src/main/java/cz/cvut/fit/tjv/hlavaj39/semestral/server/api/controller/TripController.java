package cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller;

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
        if (trip.getParticipants() != null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Forced participants");
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
        if (trip.getId() != null || trip.getParticipants() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Forced ID or participants");
        }
        trip.setId(id);
        return tripService.update(trip);
    }

    @DeleteMapping("trips/{id}")
    void deleteTrip(@PathVariable int id){
        tripService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trip Not Found")
        );
        tripService.deleteById(id);
    }
}
