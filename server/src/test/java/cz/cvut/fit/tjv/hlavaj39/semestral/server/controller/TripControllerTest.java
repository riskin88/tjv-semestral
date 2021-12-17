package cz.cvut.fit.tjv.hlavaj39.semestral.server.controller;

import cz.cvut.fit.tjv.hlavaj39.semestral.server.api.controller.TripController;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.business.TripService;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Scout;
import cz.cvut.fit.tjv.hlavaj39.semestral.server.domain.Trip;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TripController.class)
public class TripControllerTest {
    @MockBean
    TripService tripService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testAll() throws Exception {
        Trip trip1 = new Trip(0);
        Trip trip2 = new Trip(1);
        List<Trip> trips = List.of(trip1, trip2);

        Mockito.when(tripService.readAll()).thenReturn(trips);

        mockMvc.perform(get("/trips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(0)))
                .andExpect(jsonPath("$[1].id", Matchers.is(1)));
    }

    @Test
    public void testOne() throws Exception {
        Trip trip = new Trip(0);

        Mockito.when(tripService.readById(0)).thenReturn(Optional.of(trip));
        mockMvc.perform(get("/trips/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)));

        Mockito.when(tripService.readById(not(eq(0)))).thenReturn(Optional.empty());
        mockMvc.perform(get("/trips/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTrip() throws Exception {
        Trip trip = new Trip(0);

        Mockito.when(tripService.readById(not(eq(0)))).thenReturn(Optional.empty());
        Mockito.when(tripService.readById(0)).thenReturn(Optional.of(trip));

        mockMvc.perform(delete("/trips/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/trips/0"))
                .andExpect(status().isNoContent());
        verify(tripService, times(1)).deleteById(0);
    }

    @Test
    public void testNewTrip() throws Exception {
        Trip trip = new Trip();
        trip.setDestination("Brno");

        Trip res = new Trip(1);
        res.setDestination("Brno");

        Mockito.when(tripService.create(trip)).thenReturn(res);

        mockMvc.perform(post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"destination\":\"Brno\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.destination", Matchers.is("Brno")));

        ArgumentCaptor<Trip> argumentCaptor = ArgumentCaptor.forClass(Trip.class);
        Mockito.verify(tripService, Mockito.times(1)).create(argumentCaptor.capture());
        Trip tripProvidedToService = argumentCaptor.getValue();
        assertNull(tripProvidedToService.getId());
        assertEquals("Brno", tripProvidedToService.getDestination());
    }

    @Test
    public void testUpdateNonExistingTrip() throws Exception {
        Mockito.when(tripService.readById(0)).thenReturn(Optional.empty());

        mockMvc.perform(put("/trips/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"destination\":\"Brno\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateTripForcedID() throws Exception {
        Trip trip = new Trip(0);
        trip.setDestination("Brno");
        Mockito.when(tripService.readById(0)).thenReturn(Optional.of(trip));

        mockMvc.perform(put("/trips/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"destination\":\"Brno\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateTrip() throws Exception {
        Trip trip = new Trip(0);
        trip.setDestination("Pragl");
        Mockito.when(tripService.readById(0)).thenReturn(Optional.of(trip));

        Trip res = new Trip(0);
        res.setDestination("Brno");
        Mockito.when(tripService.update(res)).thenReturn(res);

        mockMvc.perform(put("/trips/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"destination\":\"Brno\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)))
                .andExpect(jsonPath("$.destination", Matchers.is("Brno")));
    }

    @Test
    public void testParticipants() throws Exception {
        Trip trip = new Trip(0);
        Set<Scout> set = new HashSet<>();
        set.add(new Scout(1));
        set.add(new Scout(2));
        trip.setParticipants(set);
        Mockito.when(tripService.readById(0)).thenReturn(Optional.of(trip));
        Mockito.when(tripService.readScoutsById(0)).thenReturn(set);

        mockMvc.perform(get("/trips/0/scouts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[1].id", Matchers.is(2)));
    }
}
