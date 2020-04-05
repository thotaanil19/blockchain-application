package com.springboot.blockchain.handlers

import com.springboot.blockchain.exceptions.UserAlreadyExistsException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    protected fun handleConflict(ex: UserAlreadyExistsException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = "Username " + ex.username + " is not available"
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.CONFLICT, request)
    }
}