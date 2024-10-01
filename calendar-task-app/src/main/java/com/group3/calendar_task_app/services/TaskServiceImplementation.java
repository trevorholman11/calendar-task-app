/**
 * This class manages the business logic related to Task entities, including saving,
 * updating, deleting, and retrieving tasks. It interacts with the TaskRepository to perform
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

import com.group3.calendar_task_app.models.Task;
import com.group3.calendar_task_app.repositories.TaskRepository;

@Service
public class TaskServiceImplementation implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

    /**
     * Saves a new Task entity in the database.
     */
	@Override
	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	/**
	 * Returns all tasks stored in the database
	 */
	@Override
	public List<Task> fetchTaskList() {
		return (List<Task>) taskRepository.findAll();
	}
	
	/**
	 * Returns a specific task based on its unique ID
	 */
	@Override
	public Optional<Task> fetchTaskById(Long taskID) {
		return taskRepository.findById(taskID);
	}

	/**
	 * Updates a specific task based on its unique ID
	 */
	@Override
	public Task updateTask(Task task, Long taskID) {
		Task taskDB = taskRepository.findById(taskID).get();
		
		if (Objects.nonNull(task.getTitle()) && !"".equalsIgnoreCase(task.getTitle())) {
			taskDB.setTitle(task.getTitle());
		}
		if (Objects.nonNull(task.getPriority())) {
			taskDB.setPriority(task.getPriority());
		}
		if (Objects.nonNull(task.getDate())) {
			taskDB.setDate(task.getDate());
		}
		if (Objects.nonNull(task.isComplete())) {
			taskDB.setComplete(task.isComplete());
		}
		if (Objects.nonNull(task.getCategory()) && !"".equalsIgnoreCase(task.getCategory())) {
			taskDB.setCategory(task.getCategory());
		}
		// Resets the reminder for the task
		if (Objects.nonNull(task.isReminder()) && Objects.nonNull(task.getReminderTime())) {
			taskDB.setReminder(task.isReminder());
			taskDB.setReminderTime(task.getReminderTime());
			taskDB.setReminderSent(false);
		}
		if (!Objects.nonNull(task.isReminder())) {
			taskDB.setReminder(false);
		}
		return taskRepository.save(taskDB);
	}

	/**
	 * Deletes a specific task based on its unique ID
	 */
	@Override
	public void deleteTaskById(Long taskID) {
		
		taskRepository.deleteById(taskID);
	}
	
    /**
     * Retrieves tasks based on the supplied category
     */
    @Override
    public List<Task> filterTasksByCategory(String category) {
        return taskRepository.findByCategory(category);
    }
    
    /**
     * Returns a list of all tasks with reminders that are due, marking them as sent when the reminder time is reached
     */
    public List<Task> getReminders() {
        List<Task> tasks = taskRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        // Check if the reminderTime is before the current time to send the reminder
        // Then mark the flag as sent so that it doesn't continue to send on every refresh after the reminder time is reached
        return tasks.stream()
        		.filter(task -> task.isReminder() && task.getReminderTime().isBefore(now) && !task.isReminderSent())            
        		.peek(task -> {
        			task.setReminderSent(true); // Set reminder sent flag to true
        			taskRepository.save(task); // Save the updated task to the database
        		}).collect(Collectors.toList());
    }
}
