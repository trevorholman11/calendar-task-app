package com.group3.calendar_task_app;
/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service

public class EventServiceImplementation implements EventService {
	
	@Autowired
	private EventRepository eventRepository;

    @Override
    public Event saveEvent(Event event) {
        if (event.isRecurring()) {
            LocalDate startDate = event.getDate();
            LocalDate endDate = event.getRecurrenceEndDate();
            
            while (!startDate.isAfter(endDate)) {
                Event recurringEvent = new Event();
                recurringEvent.setTitle(event.getTitle());
                recurringEvent.setDate(startDate);
                recurringEvent.setTime(event.getTime());
                recurringEvent.setCategory(event.getCategory());
                recurringEvent.setLocation(event.getLocation());
                recurringEvent.setRecurring(false);

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
            eventRepository.save(event);
        }
        return event;
    }
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> filterEventsByDateRange(LocalDate startDate, LocalDate endDate) {
        return eventRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<Event> filterEventsByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

    @Override
    public List<Event> filterEventsByRecurring(boolean recurring) {
        return eventRepository.findByRecurring(recurring);
    }

    @Scheduled(fixedRate = 60000) // Check every minute
    public void checkReminders() {
        List<Event> events = eventRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Event event : events) {
            if (event.isReminder() && event.getReminderTime().isBefore(now)) {
                sendReminder(event);
            }
        }
    }

    private void sendReminder(Event event) {
        // Simple placeholder for notification logic
        System.out.println("Reminder: " + event.getTitle() + " is happening on " + event.getDate() + " at " + event.getTime());
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
		if (Objects.nonNull(event.getTime()) && !"".equalsIgnoreCase(event.getTime())) {
			eventDB.setTime(event.getTime());
		}
		if (Objects.nonNull(event.isRecurring())) {
			eventDB.setRecurring(event.isRecurring());
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
