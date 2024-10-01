package com.group3.calendar_task_app.controller;

import com.group3.calendar_task_app.models.Event;
import com.group3.calendar_task_app.models.Task;
import com.group3.calendar_task_app.services.EventService;
import com.group3.calendar_task_app.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CalendarControllerTest {

    @InjectMocks
    private CalendarController calendarController;

    @Mock
    private EventService eventService;

    @Mock
    private TaskService taskService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHome() {
        String viewName = calendarController.home(model);
        assertEquals("index", viewName);
    }

    @Test
    void testEventForm() {
        String viewName = calendarController.eventForm(model);
        verify(model).addAttribute(eq("event"), any(Event.class));
        assertEquals("events", viewName);
    }

    @Test
    void testEventSubmit() {
        Event event = new Event();
        event.setTitle("Test Event");

        when(eventService.saveEvent(event)).thenReturn(event);

        String viewName = calendarController.eventSubmit(event, model);
        verify(model).addAttribute("event", event);
        assertEquals("/eventResults", viewName);
    }

    @Test
    void testFilterEvents() {
        String category = "Work";
        List<Event> events = new ArrayList<>();
        when(eventService.filterEventsByCategory(category)).thenReturn(events);

        String viewName = calendarController.filterEvents(category, model);
        verify(model).addAttribute("events", events);
        assertEquals("eventList", viewName);
    }

    @Test
    void testGetEventsJson() {
        List<Event> events = new ArrayList<>();
        when(eventService.fetchEventList()).thenReturn(events);

        List<Event> returnedEvents = calendarController.getEventsJson();
        assertEquals(events, returnedEvents);
    }

    @Test
    void testShowUpdateForm() {
        long eventId = 1L;
        Event event = new Event();
        when(eventService.fetchEventById(eventId)).thenReturn(Optional.of(event));

        String viewName = calendarController.showUpdateForm(eventId, model);
        verify(model).addAttribute("event", event);
        assertEquals("updateEvent", viewName);
    }

    @Test
    void testDeleteEvent() {
        long eventId = 1L;
        String viewName = calendarController.deleteEvent(eventId);
        verify(eventService).deleteEventById(eventId);
        assertEquals("redirect:/events/table", viewName);
    }
}
