package com.cmp.todo.domain.register.usecase

import com.cmp.todo.data_remote.register.remote.dto.RegisterRequest
import com.cmp.todo.data_remote.register.remote.dto.RegisterResponseDto
import com.cmp.todo.domain.register.entity.RegisterEntity
import com.cmp.todo.domain.register.repository.RegisterRepository
import com.cmp.todo.util.BaseResult
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(
    private val registerRepository: RegisterRepository
) {
    suspend fun execute(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, RegisterResponseDto>>  {
        return registerRepository.register(registerRequest)
    }
}