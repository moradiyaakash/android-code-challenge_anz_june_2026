package com.anz.challenge

import com.anz.challenge.common.ApiResult
import com.anz.challenge.common.AppError
import com.anz.challenge.feature.users.domain.models.User
import com.anz.challenge.feature.users.domain.repository.UsersRepository
import com.anz.challenge.feature.users.presentation.UsersUiState
import com.anz.challenge.feature.users.presentation.UsersViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {

    private val repository: UsersRepository = mockk()
    private lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when repository returns success then state is Success`() = runTest {

        coEvery { repository.getUsers() } returns ApiResult.Success(fakeUsers())

        viewModel = UsersViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(viewModel.uiState.value is UsersUiState.Success)
        assertEquals((state as UsersUiState.Success).users, fakeUsers())
    }

    @Test
    fun `when repository returns error then state is Error`() = runTest {

        coEvery { repository.getUsers() } returns ApiResult.Error(AppError.ServerError)

        viewModel = UsersViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.uiState.value as UsersUiState.Error
        assertEquals(AppError.ServerError, state.error)
    }

    private fun fakeUsers() = listOf(
        User(
            id = 1,
            name = "Melissa Pacocha",
            company = "Nienow, Kub and Mertz",
            username = "Jordyn64",
            email = "Malinda76@hotmail.com",
            address = "487 Stracke Flats",
            zip = "03985-6749",
            state = "Alabama",
            country = "Belgium",
            phone = "536-489-5995 x598",
            photo = "https://json-server.dev/ai-profiles/78.png"
        )
    )
}