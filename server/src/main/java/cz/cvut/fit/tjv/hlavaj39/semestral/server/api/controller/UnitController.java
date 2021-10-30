package cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.UnitService;
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
        unitService.create(unit);
        return unitService.readById(unit.getNumber()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.I_AM_A_TEAPOT, "Unit Not Found")
        );
    }

    @JsonView(Views.Brief.class)
    @GetMapping("/units")
    Collection<Unit> all(){
        return unitService.readAll();
    }

    @JsonView(Views.All.class)
    @GetMapping("/units/{id}")
    Unit one(@PathVariable int id){
        return unitService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.I_AM_A_TEAPOT, "Unit Not Found")
        );
    }

    @PutMapping("/units/{id}")
    Unit updateUnit(@RequestBody Unit unit, @PathVariable int id){
        unitService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.I_AM_A_TEAPOT, "Unit Not Found")
        );
        unit.setNumber(id);
        unitService.update(unit);
        return unit;
    }

    @DeleteMapping("units/{id}")
    void deleteUnit(@PathVariable int id){
        unitService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.I_AM_A_TEAPOT, "Unit Not Found")
        );
        unitService.deleteById(id);
    }
}
