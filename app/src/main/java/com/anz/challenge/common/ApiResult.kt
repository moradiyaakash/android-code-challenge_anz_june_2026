package com.anz.challenge.common

sealed class ApiResult<out T> {
    data object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val error: AppError) : ApiResult<Nothing>()
}