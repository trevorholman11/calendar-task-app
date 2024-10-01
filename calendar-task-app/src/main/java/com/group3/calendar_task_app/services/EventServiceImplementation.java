/**
 * This class manages the business logic related to Event entities, including saving,
 * updating, deleting, and retrieving events. It interacts with the EventRepository to perform
 * data access operations.
 */

package com.group3.calendar_task_app.services;
/*
 */

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group3.calendar_task_app.models.Event;
import com.group3.calendar_task_app.repositories.EventRepository;

@Service
public class EventServiceImplementation implements EventService {
	
	@Autowired
	private EventRepository eventRepository;

    /**
     * Saves a new Event entity in the database. If the event is recurring, it creates
     * and saves all instances of the recurring event based on the specified recurrence type
     * until the recurrence end date is reached.
     */
    @Override
    public Event saveEvent(Event event) {
    	
    	// Recurring event will need to be created individually
        if (event.isRecurring()) {
            LocalDateTime startDate = event.getDate();
            LocalDateTime endDate = event.getRecurrenceEndDate();
            
            // Keep creating recurring events until the recurrenceEndDate is reached
            while (!startDate.isAfter(endDate)) {
                Event recurringEvent = new Event();
                recurringEvent.setTitle(event.getTitle());
                recurringEvent.setDate(startDate);
                recurringEvent.setCategory(event.getCategory());
                recurringEvent.setLocation(event.getLocation());
                recurringEvent.setRecurring(event.isRecurring());
                recurringEvent.setRecurrenceEndDate(endDate);
                recurringEvent.setRecurrenceType(event.getRecurrenceType());
                recurringEvent.setReminder(event.isReminder());
                recurringEvent.setReminderTime(event.getReminderTime());

                eventRepository.save(recurringEvent);

                // Check the type of the recurrence and add the corresponding amount to the start date
                switch (event.getRecurrenceType()) {
                    case "daily":
                        startDate = startDate.plusDays(1);		// Adds 1 day to the start date
                        break;
                    case "weekly":
                        startDate = startDate.plusWeeks(1);		// Adds 1 week to the start date
                        break;
                    case "monthly":
                        startDate = startDate.plusMonths(1);	// Adds 1 month to the start date
                        break;
                    case "yearly":
                        startDate = startDate.plusYears(1);		// Adds 1 year to the start date
                        break;
                }
            }
        } else {
        	event.setRecurrenceType(null); //Logic in events.html will automatically set it to "daily" if recurrence is not checked
            eventRepository.save(event);
        }
        return event;
    }
 
    /**
     * Retrieves events based on the supplied category
     */
    @Override
    public List<Event> filterEventsByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

    /**
     * Returns a list of all events with reminders that are due, marking them as sent when the reminder time is reached
     */
    public List<Event> getReminders() {
        List<Event> events = eventRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        // Check if the reminderTime is before the current time to send the reminder
        // Then mark the flag as sent so that it doesn't continue to send on every refresh after the reminder time is reached
        return events.stream()
        		.filter(event -> event.isReminder() && event.getReminderTime().isBefore(now) && !event.isReminderSent())            
        		.peek(event -> {
        			event.setReminderSent(true); // Set reminder sent flag to true
        			eventRepository.save(event); // Save the updated event to the database
        		}).collect(Collectors.toList());
    }
    
    /**
     * Returns a list of all events stored in the database
     */
	@Override
	public List<Event> fetchEventList() {
		return (List<Event>) eventRepository.findAll();
	}
	
	/**
	 * Returns a specific event based on the unique ID
	 */
	@Override
	public Optional<Event> fetchEventById(Long eventID) {
		return eventRepository.findById(eventID);
	}

	/**
	 * Updates a specific event based on the unique ID
	 */
	@Override
	public Event updateEvent(Event event, Long eventID) {
		Event eventDB = eventRepository.findById(eventID).get();
		
		if (Objects.nonNull(event.getTitle()) && !"".equalsIgnoreCase(event.getTitle())) {
			eventDB.setTitle(event.getTitle());
		}
		if (Objects.nonNull(event.getDate())) {
			eventDB.setDate(event.getDate());
		}

		if (Objects.nonNull(event.isRecurring())) {
			eventDB.setRecurring(event.isRecurring());
			
			// Same logic here as the saveEvent() method
			// Can likely pull out into another method for reuse
			if (event.isRecurring()) {
	            LocalDateTime startDate = event.getDate();
	            LocalDateTime endDate = event.getRecurrenceEndDate();
				while (!startDate.isAfter(endDate)) {
	                Event recurringEvent = new Event();
	                recurringEvent.setTitle(event.getTitle());
	                recurringEvent.setDate(startDate);
	                recurringEvent.setCategory(event.getCategory());
	                recurringEvent.setLocation(event.getLocation());
	                recurringEvent.setRecurring(event.isRecurring());
	                recurringEvent.setRecurrenceEndDate(endDate);
	                recurringEvent.setRecurrenceType(event.getRecurrenceType());
	                recurringEvent.setReminder(event.isReminder());
	                recurringEvent.setReminderTime(event.getReminderTime());
	                recurringEvent.setReminderSent(false);

	                if (!event.getDate().equals(recurringEvent.getDate())) {
		                eventRepository.save(recurringEvent);
	                } else {
	                	recurringEvent.setId(event.getId());
	                	eventRepository.save(recurringEvent);
	                }

	                switch (event.getRecurrenceType()) {
	                    case "daily":
	                        startDate = startDate.plusDays(1);
	                        break;
	                    case "weekly":
	                        startDate = startDate.plusWeeks(1);
	                        break;
	                    case "monthly":
	                        startDate = startDate.plusMonths(1);
	                        break;
	                    case "yearly":
	                        startDate = startDate.plusYears(1);
	                        break;
	                }
	            }
	        } else {
	        	event.setRecurrenceType(null); //Logic in events.html will automatically set it to "daily" if recurrence is not checked
	            eventRepository.save(event);
	        }
		}
		if (Objects.nonNull(event.getCategory()) && !"".equalsIgnoreCase(event.getCategory())) {
			eventDB.setCategory(event.getCategory());
		}
		if (Objects.nonNull(event.getLocation()) && !"".equalsIgnoreCase(event.getLocation())) {
			eventDB.setLocation(event.getLocation());
		}
		
		// Used to set a new reminder
		if (Objects.nonNull(event.isReminder()) && Objects.nonNull(event.getReminderTime())) {
			eventDB.setReminder(event.isReminder());
			eventDB.setReminderTime(event.getReminderTime());
			eventDB.setReminderSent(false);
		}
		
		if (!Objects.nonNull(event.isReminder())) {
			eventDB.setReminder(false);
		}
		
		return eventRepository.save(eventDB);
	}

	/**
	 * Deletes a specific event based on its unique ID
	 */
	@Override
	public void deleteEventById(Long eventID) {
		eventRepository.deleteById(eventID);
	}

}
