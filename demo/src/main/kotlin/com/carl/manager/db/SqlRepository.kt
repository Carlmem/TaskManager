package com.carl.manager.db

import com.carl.manager.task.Status
import com.carl.manager.task.Task
import org.jetbrains.annotations.Nullable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.math.BigInteger
import java.sql.ResultSet
import java.sql.Statement

@Repository
class SqlRepository @Autowired constructor(val jdbcTemplate: JdbcTemplate) {

    fun findTaskById(id: Int): Task? {
        val sql = "SELECT * FROM tasks WHERE task_id = ?;"
        val rs = ResultSetExtractor { rs: ResultSet ->
            var task: Task? = null
            if (rs.next()) {
                task = Task(
                    rs.getString("name"),
                    rs.getString("description"),
                    Status.valueOf(rs.getString("status")),
                    rs.getLong("task_id"))
            }
            task
        }
        return jdbcTemplate.query(sql, rs, id)
    }

    fun taskInserter(@Nullable vararg args: Any): BigInteger {
        val sql = "INSERT INTO tasks (`name`, `status`, `description`) VALUES (?, ?, ?);"
        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            for (i in args.indices) {
                ps.setObject(i + 1, args[i])
            }
            ps
        }, keyHolder)

        return keyHolder.key as BigInteger
    }

    fun getTasks(): List<Task>? {
        val sql = "SELECT * FROM tasks"
        val rs = ResultSetExtractor { rs: ResultSet ->
            val tasks = mutableListOf<Task>()
            while (rs.next()) {
                tasks.add(
                    Task(
                        rs.getString("name"),
                        rs.getString("description"),
                        Status.valueOf(rs.getString("status")),
                        rs.getLong("task_id")
                    )
                )
            }
            tasks
        }

        return jdbcTemplate.query(sql, rs)
    }

    fun removeTaskById(id: Int) {
        val sql = "DELETE FROM tasks WHERE task_id = ?;"
        jdbcTemplate.update(sql, id)
    }

    fun updateTaskById(id: Int, task: Task) {
        val sql = "UPDATE tasks SET name = ?, status = ?, description = ? WHERE task_id = ?;"
        jdbcTemplate.update(sql, task.name, task.status.name, task.description, id)
    }
}