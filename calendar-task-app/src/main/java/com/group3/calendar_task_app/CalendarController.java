package com.group3.calendar_task_app;

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

@Controller
public class CalendarController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private TaskService taskService;
	
	// Route to home page
    @GetMapping("/")
    public String home(Model model) {
        return "index";  // This refers to the "index.html" template
    }
    
    // Route to get event info
    @GetMapping("/events")
    public String eventForm(Model model) {
    	model.addAttribute("event", new Event());
    	return "events";
    }
    
    @GetMapping("/events/filter")
    public String filterEvents(@RequestParam(required = false) String category, Model model) {
        List<Event> filteredEvents;

        if (category != null) {
            filteredEvents = eventService.filterEventsByCategory(category);
        } else {
            filteredEvents = eventService.getAllEvents();
        }

        model.addAttribute("events", filteredEvents);
        return "eventList";
    }
    
    // Route to submit event info
    @PostMapping("/events")
    public String eventSubmit(@ModelAttribute Event event, Model model) {
        	eventService.saveEvent(event);
        	model.addAttribute("event", event);
        	return "/results";
    }
    
 // JSON route for the JavaScript calendar (kept as-is)
    @GetMapping("/events/list")
    @ResponseBody
    public List<Event> getEventsJson() {
        return eventService.fetchEventList();
    }

    // New route for rendering the Thymeleaf view of the events
    @GetMapping("/events/table")
    public String getEventsTable(Model model) {
        List<Event> events = eventService.fetchEventList();
        model.addAttribute("events", events);
        return "eventList";  // Thymeleaf template for rendering the table
    }


    
    // Route to handle editing of an event
    // Can likely be improved by removing the Optional<Event>
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

    // Route to handle updating of an event
    @PostMapping("/events/update/{id}")
    public String updateEvent(@PathVariable long id, @ModelAttribute Event event, Model model) {
        eventService.updateEvent(event, id);
        return "redirect:/events/table";
    }

    // Route to handle deleting an event
    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable long id) {
        eventService.deleteEventById(id); 
        return "redirect:/events/table";  
    }
    
    @GetMapping("/events/reminders")
    @ResponseBody
    public List<Event> getReminders() {
        List<Event> reminders = eventService.getReminders();
        return reminders;
    }
    
    // Route to get task info
    @GetMapping("/tasks")
    public String taskForm(Model model) {
    	Task task = new Task();
    	task.setPriority(1);
    	model.addAttribute("task", task);
    	return "tasks";
    }
    
    // Route to submit event info
    @PostMapping("/tasks")
    public String taskSubmit(@ModelAttribute Task task, Model model) {
    	taskService.saveTask(task);
    	model.addAttribute("task", task);
    	return "tresults";
    }
    
    // Route to get list of tasks created
    @GetMapping("/tasks/list")
    @ResponseBody
    public List<Task> listTasks() {
    	return taskService.fetchTaskList();
    }

    // New route for rendering the Thymeleaf view of the events
    @GetMapping("/tasks/table")
    public String getTasksTable(Model model) {
        List<Task> tasks = taskService.fetchTaskList();
        model.addAttribute("tasks", tasks);
        return "taskList";  // Thymeleaf template for rendering the table
    }


    // Route to handle editing of an task
    // Can likely be improved by removing the Optional<Task>
    @GetMapping("/tasks/edit/{id}")
    public String showUpdateTaskForm(@PathVariable long id, Model model) {
        Optional<Task> taskOptional = taskService.fetchTaskById(id);

        //Check to see if the event was found
        if (taskOptional.isPresent()) {
            model.addAttribute("task", taskOptional.get());  // get the actual task, not optional one
            return "updateTask";  
        } else {
            return "redirect:/tasks/table"; // If no task then redirect
        }
    }

    // Route to handle updating of an task
    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable long id, @ModelAttribute Task task, Model model) {
        taskService.updateTask(task, id);
        return "redirect:/tasks/table";
    }

    // Route to handle deleting an task
    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable long id) {
        taskService.deleteTaskById(id); 
        return "redirect:/tasks/table";  
    }
    
    @GetMapping("/tasks/filter")
    public String filterTasks(@RequestParam(required = false) String category, Model model) {
        List<Task> filteredTasks;

        if (category != null) {
            filteredTasks = taskService.filterTasksByCategory(category);
        } else {
            filteredTasks = taskService.getAllTasks();
        }

        model.addAttribute("tasks", filteredTasks);
        return "taskList";
    }
    
    @GetMapping("/tasks/reminders")
    @ResponseBody
    public List<Task> getTaskReminders() {
        List<Task> reminders = taskService.getReminders();
        return reminders;
    }
    
}
