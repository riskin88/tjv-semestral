package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractCrudService<K, E> {

    protected final JpaRepository<E, K> repository;

    protected AbstractCrudService(JpaRepository<E, K> repository) {
        this.repository = repository;
    }


    public E create(E entity) throws EntityStateException {
        if (exists(entity))
            throw new EntityStateException(entity);
        return repository.save(entity);
    }

    public abstract boolean exists(E entity);

    public Optional<E> readById(K id) {
        return repository.findById(id);
    }


    public Collection<E> readAll() {
        return repository.findAll();
    }

    public E update(E entity) throws EntityStateException {
        if (exists(entity))
            return repository.save(entity);
        else
            throw new EntityStateException(entity);
    }

    public void deleteById(K id) {
        if (readById(id).isPresent())
            repository.deleteById(id);
    }
}
