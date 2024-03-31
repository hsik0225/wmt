package com.hsik.wmt.domain.todo.task.api.v1

import com.hsik.wmt.common.Replies
import com.hsik.wmt.common.Reply
import com.hsik.wmt.common.toReplies
import com.hsik.wmt.common.toReply
import com.hsik.wmt.domain.todo.task.TaskFinder
import com.hsik.wmt.domain.todo.task.TaskInteraction
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "태스크")
@RestController
@RequestMapping("/v1/tasks")
class TaskController(
    private val taskFinder: TaskFinder,
    private val taskInteraction: TaskInteraction,
) {
    @Operation(summary = "목록 조회")
    @GetMapping
    fun findAll(): Replies<TaskResources.Response.Me> {
        val tasks = taskFinder.findAll()
        return tasks.map { TaskResources.Response.Me.from(it) }.toReplies()
    }

    @Operation(summary = "단건 조회")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long): Reply<TaskResources.Response.Me> {
        val task = taskFinder.findById(id)
        return TaskResources.Response.Me.from(task).toReply()
    }

    @Operation(summary = "생성")
    @PostMapping
    fun create(@RequestBody request: TaskResources.Request.Create): Reply<Long> {
        val taskId = taskInteraction.create(request)
        return taskId.toReply()
    }

    @Operation(summary = "전체 수정")
    @PutMapping("/{id}")
    fun modify(
        @PathVariable id: Long,
        @RequestBody request: TaskResources.Request.Modify,
    ): Reply<Unit> {
        taskInteraction.modify(id, request)
        return Unit.toReply()
    }

    @Operation(summary = "타이틀 수정")
    @PatchMapping("/{id}")
    fun editTitle(
        @PathVariable id: Long,
        @RequestBody request: TaskResources.Request.EditTitle,
    ): Reply<Unit> {
        taskInteraction.editName(id, request.title)
        return Unit.toReply()
    }

    @Operation(summary = "삭제")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Reply<Unit> {
        taskInteraction.delete(id)
        return Unit.toReply()
    }
}
