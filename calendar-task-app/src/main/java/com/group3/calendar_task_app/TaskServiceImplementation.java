package com.group3.calendar_task_app;
/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class TaskServiceImplementation implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public List<Task> fetchTaskList() {
		return (List<Task>) taskRepository.findAll();
	}
	
	@Override
	public Optional<Task> fetchTaskById(Long taskID) {
		return taskRepository.findById(taskID);
	}

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

	@Override
	public void deleteTaskById(Long taskID) {
		
		taskRepository.deleteById(taskID);
	}
	
    @Override
    public List<Task> filterTasksByCategory(String category) {
        return taskRepository.findByCategory(category);
    }

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

    public List<Task> getReminders() {
        List<Task> tasks = taskRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        return tasks.stream()
        		.filter(task -> task.isReminder() && task.getReminderTime().isBefore(now) && !task.isReminderSent())            
        		.peek(task -> {
        			task.setReminderSent(true); // Set reminderSent to true
        			taskRepository.save(task); // Save the updated task to the database
        		}).collect(Collectors.toList());
    }
}
