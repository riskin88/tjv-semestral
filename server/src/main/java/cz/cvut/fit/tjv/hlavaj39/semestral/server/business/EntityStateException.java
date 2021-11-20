package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

public class EntityStateException extends RuntimeException {
    public <E> EntityStateException(E entity) {
        super("Illegal state of entity " + entity);
    }
}
