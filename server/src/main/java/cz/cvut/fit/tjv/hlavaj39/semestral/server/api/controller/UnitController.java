package cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.UnitService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Unit;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class UnitController {
    private final UnitService unitService;

    UnitController(UnitService unitService) {this.unitService = unitService;}

    @PostMapping("/units")
    Unit newUnit (@RequestBody Unit unit){
        if (unit.getMembers() != null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Forced members");
        unitService.create(unit);
        return unitService.readById(unit.getNumber()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Unit creation failed")
        );
    }

    @GetMapping("/units")
    Collection<Unit> all(){
        return unitService.readAll();
    }

    @GetMapping("/units/by")
    Collection<Unit> byLocation(@RequestParam(name = "location") String loc){
        return unitService.readByLocation(loc);
    }

    @GetMapping("/units/{id}")
    Unit one(@PathVariable int id){
        return unitService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unit Not Found")
        );
    }

    @GetMapping("/units/{id}/scouts")
    Collection<Scout> members(@PathVariable int id){
        unitService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unit Not Found"));
        return unitService.readScoutsById(id);
    }

    @PutMapping("/units/{id}")
    Unit updateUnit(@RequestBody Unit unit, @PathVariable int id){
        if (unit.getNumber() != null || unit.getMembers() != null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Forced ID or members");
        unitService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unit Not Found")
        );
        unit.setNumber(id);
        unitService.update(unit);
        return unit;
    }

    @DeleteMapping("units/{id}")
    void deleteUnit(@PathVariable int id){
        unitService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unit Not Found")
        );
        unitService.deleteById(id);
    }
}
