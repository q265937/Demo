package com.event.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.event.entity.Event;
import com.event.repository.EventRepository;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepo;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewEvent_Success() {
        Event event = new Event();
        event.setEventName("Music Fest");

        when(eventRepo.save(event)).thenReturn(event);

        Event result = eventService.newEvent(event);

        assertEquals("Music Fest", result.getEventName());
        verify(eventRepo).save(event);
    }

    @Test
    void testNewEvent_NullInput() {
        assertThrows(RuntimeException.class, () -> eventService.newEvent(null));
    }

    @Test
    void testAllEvents() {
        Event e1 = new Event();
        Event e2 = new Event();

        when(eventRepo.findAll()).thenReturn(List.of(e1, e2));

        List<Event> result = eventService.allEvents();

        assertEquals(2, result.size());
    }

    @Test
    void testRemoveEvent_Success() {
        Event event = new Event();
        event.setEventId(1);

        when(eventRepo.findById(1)).thenReturn(Optional.of(event));

        Event result = eventService.removeEvent(1);

        assertEquals(1, result.getEventId());
        verify(eventRepo).delete(event);
    }

    @Test
    void testRemoveEvent_NotFound() {
        when(eventRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.removeEvent(99));
    }
//
    @Test
    void testFindById_Success() {
        Event event = new Event();
        event.setEventId(1);

        when(eventRepo.findById(1)).thenReturn(Optional.of(event));

        Event result = eventService.findbyId(1);

        assertEquals(1, result.getEventId());
    }

    @Test
    void testFindById_NotFound() {
        when(eventRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.findbyId(99));
    }

    @Test
    void testUpdateLocation_Success() {
        Event event = new Event();
        event.setEventId(1);
        event.setLocation("Old Location");

        when(eventRepo.findById(1)).thenReturn(Optional.of(event));
        when(eventRepo.save(any(Event.class))).thenReturn(event);

        Event result = eventService.updateLocation("New Location", 1);

        assertEquals("New Location", result.getLocation());
    }

    @Test
    void testUpdateLocation_NotFound() {
        when(eventRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.updateLocation("New Location", 99));
    }

    @Test
    void testUpdateDate_Success() {
        Event event = new Event();
        event.setEventId(1);
        LocalDateTime newDate = LocalDateTime.of(2025, 9, 1, 18, 0);

        when(eventRepo.findById(1)).thenReturn(Optional.of(event));
        when(eventRepo.save(any(Event.class))).thenReturn(event);

        Event result = eventService.updateDate(newDate, 1);

        assertEquals(newDate, result.getEventDate());
    }

    @Test
    void testUpdateDate_NotFound() {
        LocalDateTime newDate = LocalDateTime.of(2025, 9, 1, 18, 0);
        when(eventRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.updateDate(newDate, 99));
    }

    @Test
    void testUpdateSeats_Success() {
        Event event = new Event();
        event.setEventId(1);
        event.setNoOfSeats(100);

        when(eventRepo.findById(1)).thenReturn(Optional.of(event));
        when(eventRepo.save(any(Event.class))).thenReturn(event);

        Event result = eventService.updateSeats(1, 10);

        assertEquals(90, result.getNoOfSeats());
    }

    @Test
    void testUpdateSeats_NotFound() {
        when(eventRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.updateSeats(99, 10));
    }
}