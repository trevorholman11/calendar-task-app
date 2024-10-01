/**
 * References:
 * https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html
 * https://spring.io/guides/gs/accessing-data-jpa
 * https://spring.io/guides/gs/handling-form-submission
 */

package com.group3.calendar_task_app;

import java.time.LocalDate;

// Jakarta Persistence offers object/relational mapping
import jakarta.persistence.*;

@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	private String title;
	private LocalDate date;
	private String time;
	private boolean isRecurring;
	private String category; // e.g., Work, Personal, Urgent
	private String location;
	
	
	/*
	 *	@ManyToOne
	private Person person;

	 */

	// No argument constructor is needed for Jarkarta
	public Event() {}
	
	// Getters and Setters
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean getIsRecurring() {
		return isRecurring;
	}

	public void setRecurring(boolean recurring) {
		isRecurring = recurring;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	// Additional methods for recurring event management, etc.
}
