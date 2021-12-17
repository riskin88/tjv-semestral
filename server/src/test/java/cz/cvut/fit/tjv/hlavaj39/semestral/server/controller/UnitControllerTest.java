package cz.cvut.fit.tjv.hlavaj39.semestral.server.controller;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller.UnitController;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.EntityStateException;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.UnitService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Unit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UnitController.class)
public class UnitControllerTest {
    @MockBean
    UnitService unitService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testAll() throws Exception {
        Unit unit1 = new Unit(0);
        Unit unit2 = new Unit(1);
        List<Unit> units = List.of(unit1, unit2);

        Mockito.when(unitService.readAll()).thenReturn(units);

        mockMvc.perform(get("/units"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].number", Matchers.is(0)))
                .andExpect(jsonPath("$[1].number", Matchers.is(1)));
    }

    @Test
    public void testOne() throws Exception {
        Unit unit = new Unit(0);

        Mockito.when(unitService.readById(0)).thenReturn(Optional.of(unit));
        mockMvc.perform(get("/units/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", Matchers.is(0)));

        Mockito.when(unitService.readById(not(eq(0)))).thenReturn(Optional.empty());
        mockMvc.perform(get("/units/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testByLocation() throws Exception {
        Unit unit1 = new Unit(0);
        unit1.setLocation("Praha");

        Unit unit2 = new Unit(1);
        unit2.setLocation("Praha");

        Mockito.when(unitService.readByLocation("Praha")).thenReturn(List.of(unit1,unit2));
        mockMvc.perform(get("/units/by?location=Praha"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].number", Matchers.is(0)))
                .andExpect(jsonPath("$.[1].number", Matchers.is(1)));
    }

    @Test
    public void testDeleteUnit() throws Exception {
        Unit unit = new Unit(0);

        Mockito.when(unitService.readById(not(eq(0)))).thenReturn(Optional.empty());
        Mockito.when(unitService.readById(0)).thenReturn(Optional.of(unit));

        mockMvc.perform(delete("/units/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/units/0"))
                .andExpect(status().isNoContent());
        verify(unitService, times(1)).deleteById(0);
    }

    @Test
    public void testNewExistingUnit() throws Exception {
        doThrow(new EntityStateException(new Unit())).when(unitService).create(any(Unit.class));

        mockMvc.perform(post("/units")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"0\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testNewUnit() throws Exception {
        Unit unit = new Unit(1);

        Mockito.when(unitService.create(unit)).thenReturn(unit);

        mockMvc.perform(post("/units")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", Matchers.is(1)));

        ArgumentCaptor<Unit> argumentCaptor = ArgumentCaptor.forClass(Unit.class);
        Mockito.verify(unitService, Mockito.times(1)).create(argumentCaptor.capture());
        Unit unitProvidedToService = argumentCaptor.getValue();
        assertEquals(1, unitProvidedToService.getNumber());
    }

    @Test
    public void testUpdateNonExistingUnit() throws Exception {
        Mockito.when(unitService.readById(0)).thenReturn(Optional.empty());

        mockMvc.perform(put("/units/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"location\":\"Pragl\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUnit() throws Exception {
        Unit unit = new Unit(0);
        unit.setLocation("Pragl");
        Mockito.when(unitService.readById(0)).thenReturn(Optional.of(unit));

        Unit res = new Unit(0);
        res.setLocation("Brno");
        Mockito.when(unitService.update(res)).thenReturn(res);

        mockMvc.perform(put("/units/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"location\":\"Brno\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", Matchers.is(0)))
                .andExpect(jsonPath("$.location", Matchers.is("Brno")));
    }

    @Test
    public void testMembers() throws Exception {
        Unit unit = new Unit(0);
        Set<Scout> set = new HashSet<>();
        set.add(new Scout(1));
        set.add(new Scout(2));
        unit.setMembers(set);
        Mockito.when(unitService.readById(0)).thenReturn(Optional.of(unit));
        Mockito.when(unitService.readScoutsById(0)).thenReturn(set);

        mockMvc.perform(get("/units/0/scouts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[1].id", Matchers.is(2)));
    }
}
