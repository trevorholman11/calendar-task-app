package com.group3.calendar_task_app;

import java.time.LocalDate;

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
	private LocalDate date;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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
}