package com.cmp.todo.domain.register.repository

import com.cmp.todo.data_remote.register.remote.dto.RegisterRequest
import com.cmp.todo.data_remote.register.remote.dto.RegisterResponseDto
import com.cmp.todo.domain.register.entity.RegisterEntity
import com.cmp.todo.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, RegisterResponseDto>>
}