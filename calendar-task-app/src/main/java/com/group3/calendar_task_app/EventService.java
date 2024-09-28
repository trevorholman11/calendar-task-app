/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

package com.group3.calendar_task_app;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventService {
	
	Event saveEvent(Event event);
	
    List<Event> getAllEvents();
    List<Event> filterEventsByDateRange(LocalDate startDate, LocalDate endDate);
    List<Event> filterEventsByCategory(String category);
    List<Event> filterEventsByRecurring(boolean recurring);

	
	List<Event> fetchEventList();
	List<Event> getReminders();
	
	Optional<Event> fetchEventById(Long eventID);
	
	Event updateEvent(Event event, Long eventID);
	
	void deleteEventById(Long eventID);

}
