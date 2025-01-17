package com.example.yota_pay.intrastructure.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun Throwable.toErrorResponse(): ErrorResponse =
    ErrorResponse(
        title = "Error response",
        errors =
            listOf(
                ErrorResponse.Error(
                    title = this::class.simpleName ?: "Error",
                    detail = this.message ?: "An error occurred",
                ),
            ),
    )

fun <T> Result<T>.toResponseEntity(): ResponseEntity<Any> =
    this.fold(
        onSuccess = { ResponseEntity.ok(it) },
        onFailure = {
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(it.toErrorResponse())
        },
    )
