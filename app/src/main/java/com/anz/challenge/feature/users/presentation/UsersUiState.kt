package com.anz.challenge.feature.users.presentation

import com.anz.challenge.common.AppError
import com.anz.challenge.feature.users.domain.models.User

sealed interface UsersUiState {
    object Loading : UsersUiState
    data class Success(val users: List<User>) : UsersUiState
    data class Error(val error: AppError) : UsersUiState
}