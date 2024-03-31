package com.hsik.wmt.domain.todo.task

import com.hsik.wmt.config.DataNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TaskFinder(
    private val taskRepository: TaskRepository,
) {
    private val notFoundMessage = "요청한 태스크가 존재하지 않습니다."

    fun findAll(): List<Task> {
        return taskRepository.findAll()
    }

    fun findById(id: Long): Task {
        return taskRepository.findByIdOrNull(id) ?: throw DataNotFoundException(
            message = notFoundMessage,
            causeBy = mapOf("태스크 ID" to id),
        )
    }
}
