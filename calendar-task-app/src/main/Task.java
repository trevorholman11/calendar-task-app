package com.group3.calendar_task_app;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private int priority; // 1: High, 2: Medium, 3: Low
	private LocalDate deadline;
	private boolean isComplete;
	private String category; // e.g., Work, Personal, Urgent
	
	/*
	@ManyToOne
	private Person person;
	*/
	
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

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public boolean getIsComplete() {
		return isComplete;
	}

	public void setComplete(boolean complete) {
		isComplete = complete;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	// Add these two transient properties to map to FullCalendar's start and end dates
	@Transient
	private Date start;
	@Transient
	private Date end;
	
	// Add getters and setters for the transient properties
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	// Add a method to convert the deadline to a Date object
	public void convertDeadlineToDate() {
		this.start = java.sql.Date.valueOf(deadline);
		this.end = java.sql.Date.valueOf(deadline);
	}
}
