package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.exceptions.BookAlreadyExistsException
import com.polarbookshop.catalogservice.domain.exceptions.BookNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BookControllerAdvice {
    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun bookNotFoundHandler(e: BookNotFoundException): String = e.message!!

    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun bookAlreadyExistsHandler(e: BookAlreadyExistsException): String = e.message!!


    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(e: MethodArgumentNotValidException ): Map<String, String> {
        return e.bindingResult.allErrors.associate { error ->
            (error as FieldError).field to error.defaultMessage!!
        }
    }
}
