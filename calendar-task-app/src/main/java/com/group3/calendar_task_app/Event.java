/**
 * References:
 * https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html
 * https://spring.io/guides/gs/accessing-data-jpa
 * https://spring.io/guides/gs/handling-form-submission
 */

package com.group3.calendar_task_app;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private String category; // e.g., Work, Personal, Urgent
	private String location;
	
    // Recurring event fields
    private boolean recurring;
    private String recurrenceType; // daily, weekly, monthly, yearly
    private LocalDate recurrenceEndDate;

    // Reminder fields
    private boolean reminder;
    private LocalDateTime reminderTime;
    private boolean reminderSent;
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
	
	public void setId(long id) {
		this.id = id;
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

	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
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

	public LocalDateTime getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(LocalDateTime reminderTime) {
		this.reminderTime = reminderTime;
	}

	public boolean isReminder() {
		return reminder;
	}

	public void setReminder(boolean reminder) {
		this.reminder = reminder;
	}

	public String getRecurrenceType() {
		return recurrenceType;
	}

	public void setRecurrenceType(String recurrenceType) {
		this.recurrenceType = recurrenceType;
	}

	public LocalDate getRecurrenceEndDate() {
		return recurrenceEndDate;
	}

	public void setRecurrenceEndDate(LocalDate recurrenceEndDate) {
		this.recurrenceEndDate = recurrenceEndDate;
	}

	public boolean isReminderSent() {
		return reminderSent;
	}

	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
	}
	
}
