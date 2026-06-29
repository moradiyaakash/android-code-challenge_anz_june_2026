package com.anz.challenge.feature.users.data.mapper

import com.anz.challenge.feature.users.data.network.models.UserDto
import com.anz.challenge.feature.users.domain.models.User

fun UserDto.toDomain(): User = User(
    id       = id,
    name     = name,
    company  = company,
    username = username,
    email    = email,
    address  = address,
    zip      = zip,
    state    = state,
    country  = country,
    phone    = phone,
    photo    = photo
)

fun List<UserDto>.toDomain(): List<User> = map { it.toDomain() }