package cz.cvut.fit.tjv.hlavaj39.semestral.server.business;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.dao.ScoutJpaRepository;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AbstractCrudServiceTest {
    @MockBean
    private ScoutJpaRepository scoutJpaRepository;

    @Autowired
    private ScoutService scoutService;

    private Scout existingScout = new Scout(0);
    private Scout nonExistingScout = new Scout(1);

    @BeforeEach
    void setUp() {
        Mockito.when(scoutJpaRepository.existsById(existingScout.getId())).thenReturn(true);
        Mockito.when(scoutJpaRepository.existsById(nonExistingScout.getId())).thenReturn(false);
        Mockito.when(scoutJpaRepository.findById(existingScout.getId())).thenReturn(Optional.of(existingScout));
        Mockito.when(scoutJpaRepository.findById(nonExistingScout.getId())).thenReturn(Optional.empty());
        Mockito.when(scoutJpaRepository.findAll()).thenReturn(List.of(existingScout));
        Mockito.when(scoutJpaRepository.save(existingScout)).thenReturn(existingScout);
        Mockito.when(scoutJpaRepository.save(nonExistingScout)).thenReturn(nonExistingScout);
    }

    @Test
    void create() {
        Assertions.assertThrows(EntityStateException.class, () -> scoutService.create(existingScout));
        Mockito.verify(scoutJpaRepository, Mockito.never()).save(existingScout);

        Assertions.assertEquals(nonExistingScout,scoutService.create(nonExistingScout));
        Mockito.verify(scoutJpaRepository, Mockito.atLeast(1)).save(nonExistingScout);
    }

    @Test
    void readById() {
        Assertions.assertEquals(Optional.of(existingScout), scoutService.readById(existingScout.getId()));
        Mockito.verify(scoutJpaRepository, Mockito.atLeast(1)).findById(existingScout.getId());

        Assertions.assertEquals(Optional.empty(), scoutService.readById(nonExistingScout.getId()));
        Mockito.verify(scoutJpaRepository, Mockito.atLeast(1)).findById(nonExistingScout.getId());
    }

    @Test
    void readAll() {
        var retCol = List.copyOf(scoutService.readAll());
        var exp = List.of(existingScout);
        Assertions.assertEquals(exp, retCol);
        Mockito.verify(scoutJpaRepository, Mockito.atLeast(1)).findAll();
    }

    @Test
    void update() {
        Assertions.assertThrows(EntityStateException.class, () -> scoutService.update(nonExistingScout));
        Mockito.verify(scoutJpaRepository, Mockito.never()).save(nonExistingScout);

        scoutService.update(existingScout);
        Mockito.verify(scoutJpaRepository, Mockito.atLeast(1)).save(existingScout);
    }

    @Test
    void deleteById() {
        scoutService.deleteById(existingScout.getId());
        Mockito.verify(scoutJpaRepository, Mockito.atLeast(1)).deleteById(existingScout.getId());
    }

}
