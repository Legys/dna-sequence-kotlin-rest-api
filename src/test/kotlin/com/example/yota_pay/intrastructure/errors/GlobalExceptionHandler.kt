package com.example.yota_pay.intrastructure.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
//    private val log = logger()

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors =
            ex.bindingResult.fieldErrors
                .map { it.field to it.defaultMessage }

//        log.info("Validation error: $errors")

        return ResponseEntity(
            ErrorResponse(
                "Validation error",
                errors.map { (field, message) -> ErrorResponse.Error(field, message ?: "Unknown error") },
            ),
            HttpStatus.BAD_REQUEST,
        )
    }
}
