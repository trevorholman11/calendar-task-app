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
		if (Objects.nonNull(task.getIsComplete())) {
			taskDB.setComplete(task.getIsComplete());
		}
		if (Objects.nonNull(task.getCategory()) && !"".equalsIgnoreCase(task.getCategory())) {
			taskDB.setCategory(task.getCategory());
		}
		return taskRepository.save(taskDB);
	}

	@Override
	public void deleteTaskById(Long taskID) {
		
		taskRepository.deleteById(taskID);
	}

}
