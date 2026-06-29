package com.anz.challenge.feature.users.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anz.challenge.common.ApiResult
import com.anz.challenge.feature.users.domain.models.User
import com.anz.challenge.feature.users.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            when (val result = usersRepository.getUsers()) {
                is ApiResult.Success ->
                    _uiState.value = UsersUiState.Success(result.data)

                is ApiResult.Error ->
                    _uiState.value = UsersUiState.Error(error = result.error)

                else -> Unit
            }
        }
    }

    fun getUserById(userId: Int): User? {
        return (_uiState.value as? UsersUiState.Success)?.users?.find { it.id == userId }
    }
}