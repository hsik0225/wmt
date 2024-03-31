package com.hsik.wmt

import com.hsik.wmt.domain.todo.task.TaskInteraction
import com.hsik.wmt.domain.todo.task.api.v1.TaskResources
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Configuration
@Transactional
class DbInitializer(
    private val taskInteraction: TaskInteraction,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        taskInteraction.create(
            request = TaskResources.Request.Create(
                title = "테스트 제목",
                content = "테스트 내용",
                startDate = LocalDate.now(),
                dueDate = LocalDate.now().plusDays(7L),
            ),
        )
    }
}
