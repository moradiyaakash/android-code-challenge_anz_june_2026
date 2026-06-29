package com.anz.challenge.di

import com.anz.challenge.feature.users.data.repository.UsersRepositoryImpl
import com.anz.challenge.feature.users.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUsersRepository(
        impl: UsersRepositoryImpl
    ): UsersRepository
}
