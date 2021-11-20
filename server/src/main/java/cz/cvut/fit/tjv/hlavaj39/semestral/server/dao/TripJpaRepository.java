package cz.cvut.fit.tjv.hlavaj39.semestral.server.dao;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripJpaRepository extends JpaRepository<Trip, Integer> {
}
