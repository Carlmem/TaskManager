package com.carl.manager.task

import com.carl.manager.task.taskservice.ITaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class TaskController @Autowired constructor(val iTaskService: ITaskService) {


    @GetMapping("/tasks")
    fun getAllTasks(): List<Task>? {
        return iTaskService.getAllTasks()
    }

    @PostMapping("/tasks")
    fun createTask(@RequestBody task: Task): ResponseEntity<Task> {
        val nTask = iTaskService.insert(task)
        return ResponseEntity.created(URI("/tasks/${nTask.id}")).body(nTask)
    }

    @GetMapping("/task/{id}")
    fun getTask(@PathVariable id: Int): Task {
        return iTaskService.getById(id)!!
    }

    @RequestMapping(value = ["/tasks/{id}"], method = [RequestMethod.DELETE, RequestMethod.GET])
    fun removeTask(@PathVariable id: Int) {
        iTaskService.removeTask(id)
    }

    @PutMapping("/tasks/{id}")
    fun updateTask(@PathVariable id: Int, @RequestBody task: Task): Task? {
        return iTaskService.updateTask(id, task)
    }
}