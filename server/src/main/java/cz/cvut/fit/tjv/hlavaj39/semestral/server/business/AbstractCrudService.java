package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractCrudService<K, E> {

    public void create(E entity){
    }

    public Optional<E> readById(K id){
        return Optional.empty();
    }

    public Collection<E> readAll(){
        return null;
    }

    public void update(E entity){
    }

    public void deleteById(K id){
    }
}
