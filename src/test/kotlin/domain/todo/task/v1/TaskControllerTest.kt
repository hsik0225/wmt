package domain.todo.task.v1

import com.hsik.wmt.domain.todo.task.TaskRepository
import com.hsik.wmt.domain.todo.task.api.v1.TaskResources
import config.FlowTestSupport
import io.kotest.assertions.asClue
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class TaskControllerTest : FlowTestSupport() {

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Test
    @DisplayName("태스크 생성 > 조회 > 전체 수정 > 타이틀 수정 > 삭제")
    fun taskTest() {
        var taskId: Long?
        val title = "테스트 타이틀"
        val content = "테스트 내용"

        val taskController = TaskControllerFlow(mockMvc)

        // 태스크 생성
        run {
            // When
            taskId = taskController.create(
                request = TaskResources.Request.Create(
                    title = title,
                    content = content,
                ),
            )

            // Then
            taskRepository.findAll().shouldHaveSingleElement { it.id == taskId }
        }

        // 태스크 조회
        run {
            // When
            val task = taskController.findOne(taskId!!)

            // Then
            task.asClue {
                it.title shouldBe title
                it.content shouldBe content
                it.modifiedAt shouldNotBe null
            }
        }

        // 태스크 전체 수정
        run {
            // Given
            val modifyRequest = TaskResources.Request.Modify(
                title = "수정 타이틀",
                content = "수정 내용",
            )
            // When
            taskController.modify(taskId!!, modifyRequest)

            // Then
            val task = taskController.findOne(taskId!!)
            task.asClue {
                it.title shouldBe modifyRequest.title
                it.content shouldBe modifyRequest.content
                it.modifiedAt shouldNotBe null
            }
        }

        // 태스크 타이틀 수정
        run {
            // Given
            val titleToEdit = "타이틀만 수정"

            // When
            taskController.editTitle(taskId!!, titleToEdit)

            // Then
            val task = taskController.findOne(taskId!!)
            task.title shouldBe titleToEdit
        }

        // 태스크 삭제
        run {
            // When
            taskController.delete(taskId!!)

            // Then
            val task = taskController.findOne(taskId!!)
            task.deleted shouldBe true
            task.deletedAt shouldNotBe null
        }
    }
}
