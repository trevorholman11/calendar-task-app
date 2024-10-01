/*
 * References: https://www.geeksforgeeks.org/spring-boot-with-h2-database/
 */

package com.group3.calendar_task_app;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

}
