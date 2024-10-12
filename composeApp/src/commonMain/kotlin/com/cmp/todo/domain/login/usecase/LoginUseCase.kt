package com.cmp.todo.domain.login.usecase

import com.cmp.todo.data_remote.login.remote.dto.LoginRequest
import com.cmp.todo.data_remote.login.remote.dto.LoginResponseDto
import com.cmp.todo.domain.login.entity.LoginEntity
import com.cmp.todo.domain.login.repository.LoginRepository
import com.cmp.todo.util.BaseResult
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponseDto>> {
        return loginRepository.login(loginRequest)
    }
}