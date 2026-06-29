package com.anz.challenge

import com.anz.challenge.common.ApiResult
import com.anz.challenge.feature.users.data.network.apis.UserApiService
import com.anz.challenge.feature.users.data.network.models.UserDto
import com.anz.challenge.feature.users.data.repository.UsersRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class UserRepositoryImplTest {

    private val userApiService: UserApiService = mockk()

    private lateinit var usersRepository: UsersRepositoryImpl

    @Before
    fun setup() {
        usersRepository = UsersRepositoryImpl(userApiService)
    }

    @Test
    fun `returns success when getUsers succeed`() = runTest {
        coEvery { userApiService.getUsers() } returns fakeUsers()

        val result = usersRepository.getUsers()

        assertTrue(result is ApiResult.Success)

        val user = (result as ApiResult.Success).data.first()

        assertEquals(1, user.id)
        assertEquals("Melissa Pacocha", user.name)
        assertEquals("Nienow, Kub and Mertz", user.company)
        assertEquals("Jordyn64", user.username)
    }

    @Test
    fun `returns error when getUsers fails`() = runTest {
        coEvery { userApiService.getUsers() } throws IOException()

        val result = usersRepository.getUsers()

        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `returns empty list when getUsers returns empty list`() = runTest {
        coEvery { userApiService.getUsers() } returns emptyList()

        val result = usersRepository.getUsers()

        val success = result as ApiResult.Success

        assertTrue(success.data.isEmpty())
    }

    private fun fakeUsers() = listOf(
        UserDto(
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