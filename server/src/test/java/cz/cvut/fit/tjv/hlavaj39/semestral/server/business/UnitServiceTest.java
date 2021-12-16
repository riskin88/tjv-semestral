package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.dao.UnitJpaRepository;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UnitServiceTest {
    @MockBean
    private UnitJpaRepository unitJpaRepository;

    @Autowired
    private UnitService unitService;

    private Unit existingUnit = new Unit(0);
    private Unit nonExistingUnit = new Unit(1);
    private Unit unitInPrague = new Unit(2);
    @BeforeEach
    void setUp() {
        Set<Scout> members = new HashSet<>();
        members.add(new Scout(0));
        existingUnit.setMembers(members);
        Set<Unit> inPrague = new HashSet<>();
        inPrague.add(unitInPrague);
        unitInPrague.setLocation("Praha");
        Mockito.when(unitJpaRepository.findById(existingUnit.getNumber())).thenReturn(Optional.of(existingUnit));
        Mockito.when(unitJpaRepository.findById(nonExistingUnit.getNumber())).thenReturn(Optional.empty());
        Mockito.when(unitJpaRepository.findAllByLocation("Praha")).thenReturn(inPrague);
        Mockito.when(unitJpaRepository.findAllByLocation(not(eq("Praha")))).thenReturn(Collections.emptySet());
    }

    @Test
    void readScoutsById(){
        Set<Scout> res = new HashSet<>();
        res.add(new Scout(0));
        Assertions.assertEquals(res, unitService.readScoutsById(existingUnit.getNumber()));
        Mockito.verify(unitJpaRepository, Mockito.atLeast(1)).findById(existingUnit.getNumber());

        Assertions.assertEquals(Collections.emptySet(), unitService.readScoutsById(nonExistingUnit.getNumber()));
        Mockito.verify(unitJpaRepository, Mockito.atLeast(1)).findById(nonExistingUnit.getNumber());
    }

    @Test
    void readByLocation() {
        Set<Unit> res = new HashSet<>();
        res.add(unitInPrague);

        Assertions.assertEquals(res, unitService.readByLocation("Praha"));
        Mockito.verify(unitJpaRepository, Mockito.atLeast(1)).findAllByLocation("Praha");

        Assertions.assertEquals(Collections.emptySet(), unitService.readByLocation("Brno"));
        Mockito.verify(unitJpaRepository, Mockito.atLeast(1)).findAllByLocation("Brno");
    }
}
