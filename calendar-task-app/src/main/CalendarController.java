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
        List<Event> upcomingEvents = eventService.fetchEventList();
        model.addAttribute("Events", upcomingEvents);
        return "index";
    }
    
    // Route to get event info
    @GetMapping("/events")
    public String eventForm(Model model) {
    	model.addAttribute("event", new Event());
    	return "events";
    }
    
    // Route to submit event info
    @PostMapping("/events")
    public String eventSubmit(@ModelAttribute Event event, Model model) {
    	eventService.saveEvent(event);
        System.out.println("Received event: " + event);
    	model.addAttribute("event", event);
    	return "redirect:/";
    }
    
    // Route to handle editing of an event
    @GetMapping("/events/edit/{id}")
    public String showUpdateForm(@PathVariable long id, Model model) {
        Optional<Event> eventOptional = eventService.fetchEventById(id);

        //Check to see if the event was found
        if (eventOptional.isPresent()) {
            model.addAttribute("event", eventOptional.get());  // get the actual event, not optional one
            return "updateEvent";  
        } else {
            return "redirect:/"; // If no event then redirect to home page
        }
    }

    // Route to handle updating of an event
    @PostMapping("/events/update/{id}")
    public String updateEvent(@PathVariable long id, @ModelAttribute Event event, Model model) {
        eventService.updateEvent(event, id);
        return "redirect:/";
    }

    // Route to handle deleting an event
    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable long id) {
        eventService.deleteEventById(id); 
        return "redirect:/";  
    }
    
    // Route to get task info
    @GetMapping("/tasks")
    public String taskForm(Model model) {
    	model.addAttribute("task", new Task());
    	return "tasks";
    }
    
    // Route to submit event info
    @PostMapping("/tasks")
    public String taskSubmit(@ModelAttribute Task task, Model model) {
    	taskService.saveTask(task);
        System.out.println("Received task: " + task);
    	model.addAttribute("task", task);
    	return "tresults";
    }
    
    // Route to get list of tasks created
    @GetMapping("/tasks/list")
    public String listTasks(Model model) {
        List<Task> tasks = taskService.fetchTaskList();
        model.addAttribute("tasks", tasks);
        return "taskList";  
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
            return "redirect:/tasks/list"; // If no task then redirect
        }
    }

    // Route to handle updating of an task
    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable long id, @ModelAttribute Task task, Model model) {
        taskService.updateTask(task, id);
        return "redirect:/tasks/list";
    }

    // Route to handle deleting an task
    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable long id) {
        taskService.deleteTaskById(id); 
        return "redirect:/tasks/list";  
    }

    @GetMapping("/events")
    @ResponseBody
    public List<Event> getEvents() {
        return eventService.fetchEventList();
    }
}
