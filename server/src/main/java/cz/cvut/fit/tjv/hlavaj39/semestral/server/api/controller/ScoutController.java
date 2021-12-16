package cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.EntityStateException;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.ScoutService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.TripService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.UnitService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Trip;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Unit;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class ScoutController {

    private final ScoutService scoutService;
    private final UnitService unitService;
    private final TripService tripService;

    ScoutController(ScoutService scoutService, UnitService unitService, TripService tripService) {
        this.scoutService = scoutService;
        this.unitService = unitService;
        this.tripService = tripService;
    }

    @PostMapping("/scouts")
    public Scout newScout (@RequestBody Scout scout){
        if (scout.getId() != null || (scout.getUnit() != null && !unitService.exists(scout.getUnit())) || scout.getTrips() != null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Forced ID, trip or non-existent unit");
       try{
           return scoutService.create(scout);
       }
       catch (EntityStateException e){
           throw new ResponseStatusException(
                   HttpStatus.INTERNAL_SERVER_ERROR, "Scout creation failed");
       }
    }

    @JsonView(Views.Brief.class)
    @GetMapping("/scouts")
    Collection<Scout> all(){
        return scoutService.readAll();
    }

    @GetMapping("/scouts/{id}")
    Scout one(@PathVariable Integer id){
        return scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found")
        );
    }

    @PutMapping("/scouts/{id}")
    Scout updateScout(@RequestBody Scout scout, @PathVariable Integer id){
        if (scout.getId() != null || scout.getUnit() != null || scout.getTrips() != null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Forced ID, trip or unit");
        scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found")
        );
        scout.setId(id);
        return scoutService.update(scout);
    }

    @DeleteMapping("scouts/{id}")
    void deleteScout(@PathVariable Integer id){
        scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found")
        );
        scoutService.deleteById(id);
    }

    // Unit methods

    @PutMapping("/scouts/{id}/unit")
    Scout updateUnit(@RequestBody Unit unit, @PathVariable Integer id){
        Scout scout = scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found"));
        if(!unitService.exists(unit)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Unit Not Found");
        }
        scout.setUnit(unit);
        return scoutService.update(scout);
    }

    @DeleteMapping("/scouts/{id}/unit")
    Scout deleteUnit(@PathVariable Integer id) {
        Scout scout = scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found")
        );
        scout.setUnit(null);
        return scoutService.update(scout);
    }

    // Trip methods

    @GetMapping("/scouts/{id}/trips")
    Collection<Trip> trips(@PathVariable int id){
        scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found"));
        return scoutService.readTripsById(id);
    }

    @PostMapping("/scouts/{id}/trips")
    Scout addTrip(@RequestBody Trip trip, @PathVariable Integer id) {
        Scout scout = scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found"));
        if (!tripService.exists(trip)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trip Not Found");
        }
        scout.addTrip(trip);
        return scoutService.update(scout);
    }

    @DeleteMapping("/scouts/{id}/trips/{id_trip}")
    Scout deleteTrip(@PathVariable Integer id, @PathVariable Integer id_trip) {
        Scout scout = scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found"));
        Trip trip = tripService.readById(id_trip).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trip Not Found"));
        scout.removeTrip(trip);
        return scoutService.update(scout);
    }

}
