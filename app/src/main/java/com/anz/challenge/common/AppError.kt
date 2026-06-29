package com.anz.challenge.common

sealed class AppError {

    data object NoInternet : AppError()

    data object Unauthorized : AppError()

    data object ServerError : AppError()

    data class Unknown(val message: String?) : AppError()
}