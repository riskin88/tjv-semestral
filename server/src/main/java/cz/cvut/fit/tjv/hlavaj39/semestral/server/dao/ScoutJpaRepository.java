package cz.cvut.fit.tjv.hlavaj39.semestral.server.dao;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoutJpaRepository extends JpaRepository<Scout, String> {
}
