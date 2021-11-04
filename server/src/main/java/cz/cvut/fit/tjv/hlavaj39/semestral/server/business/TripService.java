package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Trip;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TripService extends AbstractCrudService<Integer, Trip>{
    public Collection<Scout> readScoutsById(Integer id){
        return null;
    }
}
