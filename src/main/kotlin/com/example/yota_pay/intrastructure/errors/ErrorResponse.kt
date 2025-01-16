package com.example.yota_pay.intrastructure.errors

data class ErrorResponse(
    val title: String,
    val errors: List<Error>,
) {
    data class Error(
        val title: String,
        val detail: String,
    )
}
