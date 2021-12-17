package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.dao.UnitJpaRepository;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Unit;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class UnitService extends AbstractCrudService<Integer, Unit>{
    private final UnitJpaRepository unitFileRepository;
    public UnitService(UnitJpaRepository unitFileRepository, ScoutService scoutService) {
        super(unitFileRepository);
        this.unitFileRepository = unitFileRepository;
        this.scoutService = scoutService;
    }

    private final ScoutService scoutService;

    @Override
    public boolean exists(Unit entity) {
        Optional<Integer> id = Optional.ofNullable(entity.getNumber());
        if (id.isEmpty())
            return false;
        return repository.existsById(id.get());
    }

    @Override
    public void deleteById(Integer id){
        Optional<Unit> unit = readById(id);
        Collection<Scout> members;
        if (unit.isPresent()) {
            members = unit.get().getMembers();
            for (Scout scout:
                    members) {
                scout.setUnit(null);
                scoutService.update(scout);
            }
        }
        repository.deleteById(id);
    }

    public Collection<Scout> readScoutsById(Integer id){
        Optional<Unit> unit = readById(id);
        if (unit.isPresent()){
            return unit.get().getMembers();
        }
        return Collections.emptySet();
    }

    public Collection<Unit> readByLocation(String location){
        return unitFileRepository.findAllByLocation(location);
    }
}
