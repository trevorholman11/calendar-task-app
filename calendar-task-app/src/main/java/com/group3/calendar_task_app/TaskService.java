/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

package com.group3.calendar_task_app;

import java.util.List;
import java.util.Optional;

public interface TaskService {
	
	Task saveTask(Task task);
	
	List<Task> fetchTaskList();
	
	Optional<Task> fetchTaskById(Long eventID);
	
	Task updateTask(Task task, Long eventID);
	
	void deleteTaskById(Long eventID);

}
