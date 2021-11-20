package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.dao.ScoutJpaRepository;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Trip;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class ScoutService extends AbstractCrudService<Integer, Scout>{
    public ScoutService(ScoutJpaRepository scoutFileRepository) {
        super(scoutFileRepository);
    }

    @Override
    public boolean exists(Scout entity) {
        Optional<Integer> id = Optional.ofNullable(entity.getId());
        if (id.isEmpty())
            return false;
        return repository.existsById(id.get());
    }

    public Collection<Trip> readTripsById(Integer id){
        Optional<Scout> scout = readById(id);
        if (scout.isPresent()){
            return scout.get().getTrips();
        }
        return Collections.emptySet();
    }
}
