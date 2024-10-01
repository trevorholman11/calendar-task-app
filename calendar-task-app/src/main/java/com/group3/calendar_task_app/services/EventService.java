/**
 * This service interface abstracts the business logic related to event creation, modification, 
 * retrieval, filtering by category, and deletion. It ensures proper handling of event operations 
 * and communication with the underlying data repository.
 */

package com.group3.calendar_task_app.services;

import java.util.List;
import java.util.Optional;

import com.group3.calendar_task_app.models.Event;

public interface EventService {
	
	/**
	 * Saves an event in the database
	 */
	Event saveEvent(Event event);
	
	/**
	 * Updates an event in the database using its unique ID
	 */
	Event updateEvent(Event event, Long eventID);
		
	/**
	 * Returns a list of events in the database filtered by the selected category
	 */
    List<Event> filterEventsByCategory(String category);
    
    /**
     * Returns a list of all events in the database
     */
	List<Event> fetchEventList();
	
	/**
	 * Returns a list of all events that have a valid reminder flag set
	 */
	List<Event> getReminders();
	
	/**
	 * Returns an event from the database using its unique ID
	 */
	Optional<Event> fetchEventById(Long eventID);
	
	/**
	 * Deletes an event in the database using its unique ID
	 */
	void deleteEventById(Long eventID);

}
