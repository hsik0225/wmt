package com.hsik.wmt.config

import com.hsik.wmt.utils.Jackson
import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest

@RestControllerAdvice
class ExceptionConfiguration {

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception, request: HttpServletRequest): GlobalErrorFormat {
        request.run { setAttribute(RequestDispatcher.ERROR_STATUS_CODE, INTERNAL_SERVER_ERROR.value()) }
        return errorForm(request, e, ErrorSource("Occurred Unexpected Exception"))
    }

    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(e: RuntimeException, request: HttpServletRequest): GlobalErrorFormat {
        request.run { setAttribute(RequestDispatcher.ERROR_STATUS_CODE, INTERNAL_SERVER_ERROR.value()) }
        return errorForm(request, e, ErrorSource("Occurred Unexpected Runtime Exception"))
    }

    @ExceptionHandler(value = [DataNotFoundException::class])
    @ResponseStatus(NOT_FOUND)
    fun dataNotFoundException(
        e: DataNotFoundException,
        request: HttpServletRequest,
    ): GlobalErrorFormat {
        request.run { setAttribute(RequestDispatcher.ERROR_STATUS_CODE, NOT_FOUND.value()) }
        return errorForm(request, e, e.error)
    }

    private fun errorForm(request: HttpServletRequest, e: Exception, error: ErrorSource): GlobalErrorFormat {
        val errorAttributeOptions = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE)
        val errorAttributes =
            DefaultErrorAttributes().getErrorAttributes(ServletWebRequest(request), errorAttributeOptions)

        errorAttributes.apply {
            this["message"] = error.message
            this["causeBy"] = error.causeBy
            this["type"] = e.javaClass.simpleName
        }

        return Jackson.getMapper().convertValue(errorAttributes, GlobalErrorFormat::class.java)
    }
}
