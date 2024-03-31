package domain.todo.task.v1

import com.hsik.wmt.common.Reply
import com.hsik.wmt.domain.todo.task.api.v1.TaskController
import com.hsik.wmt.domain.todo.task.api.v1.TaskResources
import com.hsik.wmt.utils.fromJson
import com.hsik.wmt.utils.toJson
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

internal class TaskControllerFlow(
    private val mockMvc: MockMvc,
) {
    fun findOne(id: Long): TaskResources.Response.Me {
        val uri = linkTo<TaskController> { findOne(id) }.toUri()
        return mockMvc.get(uri)
            .andExpect {
                status { is2xxSuccessful() }
            }.andReturn()
            .response
            .contentAsString
            .fromJson<Reply<TaskResources.Response.Me>>()
            .content!!
    }

    fun create(request: TaskResources.Request.Create): Long {
        val uri = linkTo<TaskController> { create(request) }.toUri()
        return mockMvc.post(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }
            .andExpect {
                status { is2xxSuccessful() }
            }.andReturn()
            .response
            .contentAsString
            .fromJson<Reply<Long>>()
            .content!!
    }

    fun modify(id: Long, request: TaskResources.Request.Modify) {
        val uri = linkTo<TaskController> { modify(id, request) }.toUri()
        mockMvc.put(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    fun editTitle(id: Long, title: String) {
        val request = TaskResources.Request.EditTitle(title)
        val uri = linkTo<TaskController> { editTitle(id, request) }.toUri()
        mockMvc.patch(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    fun delete(id: Long) {
        val uri = linkTo<TaskController> { delete(id) }.toUri()
        mockMvc.delete(uri)
            .andExpect {
                status { is2xxSuccessful() }
            }
    }
}
