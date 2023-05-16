package com.carl.manager.task.taskservice

import com.carl.manager.task.Task

interface ITaskService {
    fun insert(task: Task): Task
    fun getById(id: Int): Task?
    fun getAllTasks(): List<Task>?
    fun removeTask(id: Int)
    fun updateTask(id: Int, task: Task) : Task?
}