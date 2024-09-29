/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

package com.group3.calendar_task_app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategory(String category);
}
