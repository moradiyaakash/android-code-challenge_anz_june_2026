package com.anz.challenge.common

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toAppError(): AppError {
    return when (this) {

        is IOException -> AppError.NoInternet

        is HttpException -> when (code()) {
            401 -> AppError.Unauthorized
            in 500..599 -> AppError.ServerError
            else -> AppError.Unknown(message())
        }

        else -> AppError.Unknown(message)
    }
}