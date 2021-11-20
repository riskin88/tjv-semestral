package cz.cvut.fit.tjv.hlavaj39.semestral.server.dao;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UnitJpaRepository extends JpaRepository<Unit, Integer> {
    @Query("SELECT u FROM Unit u WHERE LOWER(u.location) LIKE LOWER(:loc)") //JPQL
    Collection<Unit> findAllByLocation(String loc);
}
