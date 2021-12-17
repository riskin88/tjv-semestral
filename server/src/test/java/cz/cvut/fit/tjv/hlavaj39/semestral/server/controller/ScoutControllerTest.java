package cz.cvut.fit.tjv.hlavaj39.semestral.server.controller;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller.ScoutController;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.ScoutService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.TripService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.UnitService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Unit;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

@WebMvcTest(ScoutController.class)
public class ScoutControllerTest {
    @MockBean
    ScoutService scoutService;

    @MockBean
    UnitService unitService;

    @MockBean
    TripService tripService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testAll() throws Exception {
        Scout scout1 = new Scout(0);
        Scout scout2 = new Scout(1);
        List<Scout> scouts = List.of(scout1, scout2);

        Mockito.when(scoutService.readAll()).thenReturn(scouts);

        mockMvc.perform(get("/scouts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(0)))
                .andExpect(jsonPath("$[1].id", Matchers.is(1)));
    }

    @Test
    public void testOne() throws Exception {
        Scout scout = new Scout(0);
        
        Mockito.when(scoutService.readById(0)).thenReturn(Optional.of(scout));
        mockMvc.perform(get("/scouts/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)));

        Mockito.when(scoutService.readById(not(eq(0)))).thenReturn(Optional.empty());
        mockMvc.perform(get("/scouts/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteScout() throws Exception {
        Scout scout = new Scout(0);

        Mockito.when(scoutService.readById(not(eq(0)))).thenReturn(Optional.empty());
        Mockito.when(scoutService.readById(0)).thenReturn(Optional.of(scout));

        mockMvc.perform(delete("/scouts/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/scouts/0"))
                .andExpect(status().isNoContent());
        verify(scoutService, times(1)).deleteById(0);
    }

    @Test
    public void testNewScout() throws Exception {
        Scout scout = new Scout();
        scout.setName("pepicek");

        Scout res = new Scout(1);
        res.setName("pepicek");

        Mockito.when(scoutService.create(scout)).thenReturn(res);

        mockMvc.perform(post("/scouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"pepicek\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("pepicek")));

        ArgumentCaptor<Scout> argumentCaptor = ArgumentCaptor.forClass(Scout.class);
        Mockito.verify(scoutService, Mockito.times(1)).create(argumentCaptor.capture());
        Scout scoutProvidedToService = argumentCaptor.getValue();
        assertNull(scoutProvidedToService.getId());
        assertEquals("pepicek", scoutProvidedToService.getName());
    }

    @Test
    public void testUpdateNonExistingScout() throws Exception {
        Mockito.when(scoutService.readById(0)).thenReturn(Optional.empty());

        mockMvc.perform(put("/scouts/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"pepicek\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateScoutForcedID() throws Exception {
        Scout scout = new Scout(0);
        scout.setName("pepicek");
        Mockito.when(scoutService.readById(0)).thenReturn(Optional.of(scout));

        mockMvc.perform(put("/scouts/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"name\":\"pepicek\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateScout() throws Exception {
        Scout scout = new Scout(0);
        scout.setName("jenicek");
        Mockito.when(scoutService.readById(0)).thenReturn(Optional.of(scout));

        Scout res = new Scout(0);
        res.setName("pepicek");
        Mockito.when(scoutService.update(res)).thenReturn(res);

        mockMvc.perform(put("/scouts/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"pepicek\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)))
                .andExpect(jsonPath("$.name", Matchers.is("pepicek")));
    }

    @Test
    public void testUpdateUnit() throws Exception {
        Scout scout = new Scout(0);
        scout.setName("pepicek");
        Mockito.when(scoutService.readById(0)).thenReturn(Optional.of(scout));

        Unit unit = new Unit(0);
        Mockito.when(unitService.exists(unit)).thenReturn(true);

        Scout res = new Scout(0);
        res.setName("pepicek");
        res.setUnit(unit);
        Mockito.when(scoutService.update(res)).thenReturn(res);

        mockMvc.perform(put("/scouts/0/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\":0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)))
                .andExpect(jsonPath("$.name", Matchers.is("pepicek")))
                .andExpect(jsonPath("$.unit.number", Matchers.is(0)));
    }

    @Test
    public void testUpdateNonExistingUnit() throws Exception {
        Scout scout = new Scout(0);
        scout.setName("pepicek");
        Mockito.when(scoutService.readById(0)).thenReturn(Optional.of(scout));

        Unit unit = new Unit(0);
        Mockito.when(unitService.exists(unit)).thenReturn(false);

        mockMvc.perform(put("/scouts/0/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isNotFound());
    }
}
