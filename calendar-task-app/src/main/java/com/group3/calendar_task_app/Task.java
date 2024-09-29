package com.group3.calendar_task_app;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String title;
	private int priority; // 1: High, 2: Medium, 3: Low
   	private LocalDateTime date;
	private boolean complete;
	private String category; // e.g., Work, Personal, Urgent
	
    // Reminder fields
    private boolean reminder;
    private LocalDateTime reminderTime;
    private boolean reminderSent;
	
	// No argument constructor is needed for Jarkarta
	public Task() {}
	
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isReminder() {
		return reminder;
	}

	public void setReminder(boolean reminder) {
		this.reminder = reminder;
	}

	public LocalDateTime getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(LocalDateTime reminderTime) {
		this.reminderTime = reminderTime;
	}

	public boolean isReminderSent() {
		return reminderSent;
	}

	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
	}
}