package com.example.qme_mobile.data.network

import com.google.gson.Gson

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

fun parseErrorMessage(errorBody: String?): String {
    return try {
        if (errorBody.isNullOrBlank()) return "Unknown server error"
        val gson = Gson()
        val errorResponse = gson.fromJson(errorBody, com.example.qme_mobile.data.model.ApiErrorResponse::class.java)
        val validationErrors = errorResponse.errors
        if (!validationErrors.isNullOrEmpty()) {
            validationErrors.values.flatten().joinToString("\n")
        } else {
            errorResponse.message.ifBlank { "Unknown server error" }
        }
    } catch (e: Exception) {
        errorBody ?: "Unknown server error"
    }
}

fun getHttpErrorMessage(code: Int): String = when (code) {
    400 -> "Bad request. Please check your input."
    401 -> "Unauthorized. Please log in again."
    403 -> "Access forbidden."
    404 -> "Resource not found."
    422 -> "Validation failed. Please check your input."
    500 -> "Server error. Please try again later."
    503 -> "Service unavailable. Please try again later."
    else -> "Something went wrong (Error $code)."
}
