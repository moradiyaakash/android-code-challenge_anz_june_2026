package com.anz.challenge.feature.users.domain.repository

import com.anz.challenge.common.ApiResult
import com.anz.challenge.feature.users.domain.models.User

interface UsersRepository {

    suspend fun getUsers(): ApiResult<List<User>>
}