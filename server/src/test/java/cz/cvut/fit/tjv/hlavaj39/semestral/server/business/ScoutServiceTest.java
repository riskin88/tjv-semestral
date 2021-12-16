package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.dao.ScoutJpaRepository;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Trip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class ScoutServiceTest {
    @MockBean
    private ScoutJpaRepository scoutJpaRepository;

    @Autowired
    private ScoutService scoutService;

    private Scout existingScout = new Scout(0);
    private Scout nonExistingScout = new Scout(1);
    @BeforeEach
    void setUp() {
        Set<Trip> set = new HashSet<>();
        set.add(new Trip(0));
        existingScout.setTrips(set);
        Mockito.when(scoutJpaRepository.findById(existingScout.getId())).thenReturn(Optional.of(existingScout));
        Mockito.when(scoutJpaRepository.findById(nonExistingScout.getId())).thenReturn(Optional.empty());
    }
    @Test
    void readTripsById(){
        Set<Trip> res = new HashSet<>();
        res.add(new Trip(0));
        Assertions.assertEquals(res, scoutService.readTripsById(existingScout.getId()));
        Mockito.verify(scoutJpaRepository, Mockito.atLeast(1)).findById(existingScout.getId());

        Assertions.assertEquals(Collections.emptySet(), scoutService.readTripsById(nonExistingScout.getId()));
        Mockito.verify(scoutJpaRepository, Mockito.atLeast(1)).findById(nonExistingScout.getId());
    }
}
