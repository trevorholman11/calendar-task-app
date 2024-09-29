package com.group3.calendar_task_app;
/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EventServiceImplementation implements EventService {
	
	@Autowired
	private EventRepository eventRepository;

    @Override
    public Event saveEvent(Event event) {
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

                eventRepository.save(recurringEvent);

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
        return event;
    }
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> filterEventsByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

    public List<Event> getReminders() {
        List<Event> events = eventRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        return events.stream()
        		.filter(event -> event.isReminder() && event.getReminderTime().isBefore(now) && !event.isReminderSent())            
        		.peek(event -> {
        			event.setReminderSent(true); // Set reminderSent to true
        			eventRepository.save(event); // Save the updated event to the database
        		}).collect(Collectors.toList());
    }
    
	@Override
	public List<Event> fetchEventList() {
		return (List<Event>) eventRepository.findAll();
	}
	
	@Override
	public Optional<Event> fetchEventById(Long eventID) {
		return eventRepository.findById(eventID);
	}

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
		
		return eventRepository.save(eventDB);
	}

	@Override
	public void deleteEventById(Long eventID) {
		
		eventRepository.deleteById(eventID);
	}

}
