package com.carl.manager.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class TableInit @Autowired constructor(jdbcTemplate: JdbcTemplate) {
    init {
        jdbcTemplate.execute(
            "CREATE TABLE IF NOT EXISTS " +
                    "tasks" +
                    " (" +
                    "task_id int(10) UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "name varchar(16) NOT NULL," +
                    "status varchar(16) NOT NULL," +
                    "description varchar(100) NOT NULL," +
                    "PRIMARY KEY (task_id), UNIQUE(name)" +
                    ")"
        )
    }
}