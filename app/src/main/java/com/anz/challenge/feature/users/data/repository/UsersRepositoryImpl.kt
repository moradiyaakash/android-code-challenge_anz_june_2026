package com.anz.challenge.feature.users.data.repository

import com.anz.challenge.common.ApiResult
import com.anz.challenge.common.toAppError
import com.anz.challenge.feature.users.data.mapper.toDomain
import com.anz.challenge.feature.users.data.network.apis.UserApiService
import com.anz.challenge.feature.users.domain.models.User
import com.anz.challenge.feature.users.domain.repository.UsersRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UsersRepositoryImpl @Inject constructor(
    private val api: UserApiService
) : UsersRepository {

    override suspend fun getUsers(): ApiResult<List<User>> {
        return try {
            ApiResult.Success(api.getUsers().toDomain())
        } catch (throwCancellation: CancellationException) {
            throw throwCancellation
        } catch (e: Exception) {
            ApiResult.Error(e.toAppError())
        }
    }
}