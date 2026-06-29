package com.anz.challenge.feature.users.data.network.apis

import com.anz.challenge.feature.users.data.network.models.UserDto
import retrofit2.http.GET

interface UserApiService {

    @GET("users")
    suspend fun getUsers(): List<UserDto>
}