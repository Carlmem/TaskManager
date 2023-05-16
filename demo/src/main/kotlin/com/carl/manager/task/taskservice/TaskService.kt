package com.carl.manager.task.taskservice

import com.carl.manager.db.SqlRepository
import com.carl.manager.task.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskService @Autowired constructor(val sqlRepository: SqlRepository) : ITaskService {

    override fun insert(task: Task): Task {
        val id: Long = sqlRepository.taskInserter(
            task.name,
            task.status.name,
            task.description
        ).longValueExact()
        return Task(task.name, task.description, task.status, id)
    }

    override fun getById(id: Int): Task? {
        return sqlRepository.findTaskById(id)
    }

    override fun getAllTasks(): List<Task>? {
        return sqlRepository.getTasks()
    }

    override fun removeTask(id: Int) {
        sqlRepository.removeTaskById(id)
    }

    override fun updateTask(id: Int, task: Task): Task? {
        sqlRepository.updateTaskById(id, task)
        return getById(id)
    }
}