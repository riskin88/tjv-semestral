package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.dao.TripJpaRepository;
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
public class TripServiceTest {
    @MockBean
    private TripJpaRepository tripJpaRepository;

    @Autowired
    private TripService tripService;

    private Trip existingTrip = new Trip(0);
    private Trip nonExistingTrip = new Trip(1);
    @BeforeEach
    void setUp() {
        Set<Scout> set = new HashSet<>();
        set.add(new Scout(0));
        existingTrip.setParticipants(set);
        Mockito.when(tripJpaRepository.findById(existingTrip.getId())).thenReturn(Optional.of(existingTrip));
        Mockito.when(tripJpaRepository.findById(nonExistingTrip.getId())).thenReturn(Optional.empty());
    }
    @Test
    void readScoutsById(){
        Set<Scout> res = new HashSet<>();
        res.add(new Scout(0));
        Assertions.assertEquals(res, tripService.readScoutsById(existingTrip.getId()));
        Mockito.verify(tripJpaRepository, Mockito.atLeast(1)).findById(existingTrip.getId());

        Assertions.assertEquals(Collections.emptySet(), tripService.readScoutsById(nonExistingTrip.getId()));
        Mockito.verify(tripJpaRepository, Mockito.atLeast(1)).findById(nonExistingTrip.getId());
    }
}
