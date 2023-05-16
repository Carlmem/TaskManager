package com.carl.manager.task

data class Task(
    val name: String,
    val description: String,
    val status: Status,
    val id: Long? = null
)
