package com.hsik.wmt.domain.todo.task

import com.hsik.wmt.domain.todo.task.api.v1.TaskResources
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskInteraction(
    private val taskFinder: TaskFinder,
    private val taskRepository: TaskRepository,
) {
    fun create(request: TaskResources.Request.Create): Long {
        val task = request.run {
            Task(
                title,
                content,
                startDate,
                dueDate,
            )
        }
        return taskRepository.save(task).id!!
    }

    fun modify(id: Long, request: TaskResources.Request.Modify) {
        val task = taskFinder.findById(id)

        task.apply {
            title = request.title
            content = request.content
            startDate = request.startDate
            dueDate = request.dueDate
        }
    }

    fun editName(id: Long, title: String) {
        val task = taskFinder.findById(id)

        task.apply {
            this.title = title
        }
    }

    fun delete(id: Long) {
        val task = taskFinder.findById(id)
        task.delete()
    }
}
