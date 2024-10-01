/**
 * This service interface abstracts the business logic related to task creation, modification, 
 * retrieval, filtering by category, and deletion. It ensures proper handling of task operations 
 * and communication with the underlying data repository.
 */

package com.group3.calendar_task_app.services;

import java.util.List;
import java.util.Optional;

import com.group3.calendar_task_app.models.Task;

public interface TaskService {
	
	/**
	 * Saves a task in the database
	 */
	Task saveTask(Task task);
	
	/**
	 * Updates a task in the database using its unique ID
	 */
	Task updateTask(Task task, Long eventID);
	
	/**
	 * Returns a list of tasks in the database filtered by the selected category
	 */
    List<Task> filterTasksByCategory(String category);
    
	/**
	 * Returns a list of all tasks that have a valid reminder flag set
	 */
	List<Task> getReminders();
	
    /**
     * Returns a list of all tasks in the database
     */
	List<Task> fetchTaskList();
	
	/**
	 * Returns a task from the database using its unique ID
	 */
	Optional<Task> fetchTaskById(Long eventID);
	
	/**
	 * Deletes a task in the database using its unique ID
	 */
	void deleteTaskById(Long eventID);
}
