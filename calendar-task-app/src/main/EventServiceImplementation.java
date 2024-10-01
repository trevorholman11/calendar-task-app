package com.group3.calendar_task_app;
/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EventServiceImplementation implements EventService {
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event saveEvent(Event event) {
		return eventRepository.save(event);
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
		if (Objects.nonNull(event.getIsRecurring())) {
			eventDB.setRecurring(event.getIsRecurring());
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
