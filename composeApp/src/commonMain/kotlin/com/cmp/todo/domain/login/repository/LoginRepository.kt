package com.cmp.todo.domain.login.repository

import com.cmp.todo.data_remote.login.remote.dto.LoginRequest
import com.cmp.todo.data_remote.login.remote.dto.LoginResponseDto
import com.cmp.todo.domain.login.entity.LoginEntity
import com.cmp.todo.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponseDto>>
}