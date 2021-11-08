package cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.ScoutService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class ScoutController {

    private final ScoutService scoutService;

    ScoutController(ScoutService scoutService) {this.scoutService = scoutService;}

    @PostMapping("/scouts")
    Scout newScout (@RequestBody Scout scout){
        scoutService.create(scout);
        return scoutService.readById(scout.getId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Scout creation failed")
        );
    }

    @JsonView(Views.Brief.class)
    @GetMapping("/scouts")
    Collection<Scout> all(){
        return scoutService.readAll();
    }

    @JsonView(Views.All.class)
    @GetMapping("/scouts/{id}")
    Scout one(@PathVariable int id){
        return scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found")
        );
    }

    @PutMapping("/scouts/{id}")
    Scout updateScout(@RequestBody Scout scout, @PathVariable int id){
        scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found")
        );
        scout.setId(id);
        scoutService.update(scout);
        return scout;
    }

    @DeleteMapping("scouts/{id}")
    void deleteScout(@PathVariable int id){
        scoutService.readById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Scout Not Found")
        );
        scoutService.deleteById(id);
    }
}
