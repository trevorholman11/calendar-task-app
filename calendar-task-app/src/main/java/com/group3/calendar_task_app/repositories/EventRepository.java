/**
 * EventRepository interface for performing CRUD operations on Event entities in the H2 database.
 * Extending the JpaRepository interface provides methods such as saving, deleting, and finding entities.
 * Custom queries can also be defined.
 * 
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

package com.group3.calendar_task_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.calendar_task_app.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	
	/**
	 * Returns a list of events based on the selected category
	 */
    List<Event> findByCategory(String category);
}

