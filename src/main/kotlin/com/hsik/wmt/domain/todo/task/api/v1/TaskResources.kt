package com.hsik.wmt.domain.todo.task.api.v1

import com.hsik.wmt.domain.todo.task.Task
import java.time.LocalDate
import java.time.LocalDateTime

class TaskResources {
    class Request {
        data class Create(
            val title: String,
            val content: String? = null,
            val startDate: LocalDate? = null,
            val dueDate: LocalDate? = null,
        )

        data class EditTitle(
            val title: String,
        )

        data class Modify(
            val title: String,
            val content: String? = null,
            val startDate: LocalDate? = null,
            val dueDate: LocalDate? = null,
        )
    }

    class Response {
        data class Me(
            val id: Long,
            val createdAt: LocalDateTime,
            val modifiedAt: LocalDateTime,
            val deletedAt: LocalDateTime? = null,
            val title: String,
            val content: String? = null,
            val status: Task.Status,
            val startDate: LocalDate? = null,
            val dueDate: LocalDate? = null,
            val deleted: Boolean,
        ) {
            companion object {
                fun from(task: Task): Me {
                    return task.run {
                        Me(
                            id = id!!,
                            createdAt = createdAt!!,
                            modifiedAt = modifiedAt!!,
                            deletedAt = deletedAt,
                            title = title,
                            content = content,
                            status = status,
                            startDate = startDate,
                            dueDate = dueDate,
                            deleted = deleted,
                        )
                    }
                }
            }
        }
    }
}
