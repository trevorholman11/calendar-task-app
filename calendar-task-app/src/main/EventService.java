/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

package com.group3.calendar_task_app;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface EventService {
	
	Event saveEvent(Event event);
	
	List<Event> fetchEventList();
	
	Optional<Event> fetchEventById(Long eventID);
	
	Event updateEvent(Event event, Long eventID);
	
	void deleteEventById(Long eventID);

}
