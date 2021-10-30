package cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.TripService;
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
        tripService.create(trip);
        return tripService.readById(trip.getId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.I_AM_A_TEAPOT, "Trip Not Found")
        );
    }

    @GetMapping("/trips")
    Collection<Trip> all(){
        return tripService.readAll();
    }

    @GetMapping("/trips/{id}")
    Trip one(@PathVariable int id){
        return tripService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.I_AM_A_TEAPOT, "Trip Not Found")
        );
    }

    @PutMapping("/trips/{id}")
    Trip updateTrip(@RequestBody Trip trip, @PathVariable int id){
        tripService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.I_AM_A_TEAPOT, "Trip Not Found")
        );
        trip.setId(id);
        tripService.update(trip);
        return trip;
    }

    @DeleteMapping("trips/{id}")
    void deleteTrip(@PathVariable int id){
        tripService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.I_AM_A_TEAPOT, "Trip Not Found")
        );
        tripService.deleteById(id);
    }
}
