package com.hsik.wmt.domain.todo.task

import com.hsik.wmt.common.Audit
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class Task(
    /* 제목 */
    @Column(length = 200)
    var title: String,
    /* 내용 */
    @Column(columnDefinition = "TEXT")
    var content: String? = null,
    /**
     * 예상 작업 시작 일자
     */
    var startDate: LocalDate? = null,
    /**
     * 예상 작업 종료 일자
     */
    var dueDate: LocalDate? = null,
) : Audit() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(25)")
    var status = Status.TODO

    enum class Status {
        TODO, IN_PROGRESS, DONE
    }

    fun todo() {
        this.status = Status.TODO
    }

    fun inProgress() {
        this.status = Status.IN_PROGRESS
    }

    fun done() {
        this.status = Status.DONE
    }

    /**
     * 태스크 삭제 여부
     */
    var deleted = false

    /**
     * 작업 삭제 일시
     */
    var deletedAt: LocalDateTime? = null

    fun delete() {
        if (deleted) {
            return
        }

        this.deleted = true
        this.deletedAt = LocalDateTime.now()
    }
}
