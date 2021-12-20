package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.dao.TripJpaRepository;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Trip;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class TripService extends AbstractCrudService<Integer, Trip>{
    public TripService(TripJpaRepository tripFileRepository, ScoutService scoutService) {
        super(tripFileRepository);
        this.scoutService = scoutService;
    }

    private final ScoutService scoutService;

    @Override
    public boolean exists(Trip entity) {
        Optional<Integer> id = Optional.ofNullable(entity.getId());
        if (id.isEmpty())
            return false;
        return repository.existsById(id.get());
    }

    @Override
    public void deleteById(Integer id){
        Optional<Trip> trip = readById(id);
        Collection<Scout> participants;
        if (trip.isPresent()) {
            participants = trip.get().getParticipants();
            for (Scout scout:
                 participants) {
                scout.removeTrip(trip.get());
                scoutService.update(scout);
            }
        }
        super.deleteById(id);
    }

    public Collection<Scout> readScoutsById(Integer id){
        Optional<Trip> trip = readById(id);
        if (trip.isPresent()){
            return trip.get().getParticipants();
        }
        return Collections.emptySet();
    }


}
