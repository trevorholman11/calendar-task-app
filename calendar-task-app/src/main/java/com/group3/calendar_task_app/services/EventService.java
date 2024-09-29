/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

package com.group3.calendar_task_app.services;

import java.util.List;
import java.util.Optional;

import com.group3.calendar_task_app.models.Event;

public interface EventService {
	
	Event saveEvent(Event event);
	
    List<Event> getAllEvents();
    List<Event> filterEventsByCategory(String category);

	
	List<Event> fetchEventList();
	List<Event> getReminders();
	
	Optional<Event> fetchEventById(Long eventID);
	
	Event updateEvent(Event event, Long eventID);
	
	void deleteEventById(Long eventID);

}