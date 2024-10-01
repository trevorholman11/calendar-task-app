/**
 * CalendarController manages the web routes and interactions between 
 * the front-end and back-end for events and tasks. It defines routes to 
 * display forms, submit data, and handle CRUD operations on events and tasks.
 * Additional routes are defined for filtering and reminders for events and tasks.
 */

package com.group3.calendar_task_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group3.calendar_task_app.models.Event;
import com.group3.calendar_task_app.models.Task;
import com.group3.calendar_task_app.services.EventService;
import com.group3.calendar_task_app.services.TaskService;

@Controller
public class CalendarController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * Displays the home page
	 */
    @GetMapping("/")
    public String home(Model model) {
        return "index";  // This refers to the "index.html" template
    }
    
    /**
     * Displays the form for creating a new event
     */
    @GetMapping("/events")
    public String eventForm(Model model) {
    	model.addAttribute("event", new Event());
    	return "events";
    }
    
    /**
     * Handles the submission of an event form and saves the newly created event to the H2 database
     */
    @PostMapping("/events")
    public String eventSubmit(@ModelAttribute Event event, Model model) {
        	eventService.saveEvent(event);
        	model.addAttribute("event", event);
        	return "/results";
    }
    
    /**
     * Filters events by category and displays the results
     */
    @GetMapping("/events/filter")
    public String filterEvents(@RequestParam(required = false) String category, Model model) {
        List<Event> filteredEvents;
        
        // Need to check for blank and empty answers as well as null as @RequestParam can bind an empty string
        if (category != null && !category.trim().isBlank() && !category.trim().isEmpty()) {
            filteredEvents = eventService.filterEventsByCategory(category);
        } else {
            filteredEvents = eventService.fetchEventList();		// Return all events if no filter
        }

        model.addAttribute("events", filteredEvents);
        return "eventList";
    }
    
    /**
     * Returns a list of events in JSON format for use with JavaScript
     */
    @GetMapping("/events/list")
    @ResponseBody
    public List<Event> getEventsJson() {
        return eventService.fetchEventList();
    }

    /**
     * Displays all events
     */
    @GetMapping("/events/table")
    public String getEventsTable(Model model) {
        List<Event> events = eventService.fetchEventList();
        model.addAttribute("events", events);
        return "eventList";  
    }

    /**
     * Displays the form for updating an event
     */
    @GetMapping("/events/edit/{id}")
    public String showUpdateForm(@PathVariable long id, Model model) {
        Optional<Event> eventOptional = eventService.fetchEventById(id);

        //Check to see if the event was found
        if (eventOptional.isPresent()) {
            model.addAttribute("event", eventOptional.get());  // get the actual event, not optional one
            return "updateEvent";  
        } else {
            return "redirect:/events/table"; // If no event then redirect
        }
    }

    /**
     * Updates the event and writes the updated info to the H2 database for the event with the matching ID
     */
    @PostMapping("/events/update/{id}")
    public String updateEvent(@PathVariable long id, @ModelAttribute Event event, Model model) {
        eventService.updateEvent(event, id);
        return "redirect:/events/table";
    }

    /**
     * Deletes the event based on ID, will remove the event from the H2 database as well
     */
    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable long id) {
        eventService.deleteEventById(id); 
        return "redirect:/events/table";  
    }
    
    /**
     * Returns a list of events marked with a reminder in JSON format for use with JavaScript
     */
    @GetMapping("/events/reminders")
    @ResponseBody
    public List<Event> getReminders() {
        List<Event> reminders = eventService.getReminders();
        return reminders;
    }
    
    /**
     * Displays the form for creating a new task
     */
    @GetMapping("/tasks")
    public String taskForm(Model model) {
    	Task task = new Task();
    	task.setPriority(1);				// Set the priority to 1 so it automatically populates in the field
    	model.addAttribute("task", task);
    	return "tasks";
    }
    
    /**
     * Handles the submission of a task form and saves the newly created task to the H2 database
     */
    @PostMapping("/tasks")
    public String taskSubmit(@ModelAttribute Task task, Model model) {
    	taskService.saveTask(task);
    	model.addAttribute("task", task);
    	return "tresults";
    }
    
    /**
     * Returns a list of tasks in JSON format for use with JavaScript
     */
    @GetMapping("/tasks/list")
    @ResponseBody
    public List<Task> listTasks() {
    	return taskService.fetchTaskList();
    }

    /**
     * Displays all tasks
     */
    @GetMapping("/tasks/table")
    public String getTasksTable(Model model) {
        List<Task> tasks = taskService.fetchTaskList();
        model.addAttribute("tasks", tasks);
        return "taskList";  
    }

    /**
     * Displays the form for updating a task
     */
    @GetMapping("/tasks/edit/{id}")
    public String showUpdateTaskForm(@PathVariable long id, Model model) {
        Optional<Task> taskOptional = taskService.fetchTaskById(id);

        //Check to see if the task was found
        if (taskOptional.isPresent()) {
            model.addAttribute("task", taskOptional.get());  // get the actual task, not optional one
            return "updateTask";  
        } else {
            return "redirect:/tasks/table"; // If no task then redirect
        }
    }

    /**
     * Updates the task and writes the updated info to the H2 database for the task with the matching ID
     */
    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable long id, @ModelAttribute Task task, Model model) {
        taskService.updateTask(task, id);
        return "redirect:/tasks/table";
    }

    /**
     * Deletes the event based on ID, will remove the event from the H2 database as well
     */
    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable long id) {
        taskService.deleteTaskById(id); 
        return "redirect:/tasks/table";  
    }
    
    /**
     * Filters events by category and displays the results
     */
    @GetMapping("/tasks/filter")
    public String filterTasks(@RequestParam(required = false) String category, Model model) {
        List<Task> filteredTasks;

        // Need to check for blank and empty answers as well as null as @RequestParam can bind an empty string
        if (category != null && !category.trim().isBlank() && !category.trim().isEmpty()) {
            filteredTasks = taskService.filterTasksByCategory(category);
        } else {
            filteredTasks = taskService.fetchTaskList();		// Return all tasks if no filter
        }

        model.addAttribute("tasks", filteredTasks);
        return "taskList";
    }
    
    /**
     * Returns a list of tasks marked with a reminder in JSON format for use with JavaScript
     */
    @GetMapping("/tasks/reminders")
    @ResponseBody
    public List<Task> getTaskReminders() {
        List<Task> reminders = taskService.getReminders();
        return reminders;
    }
    
}
