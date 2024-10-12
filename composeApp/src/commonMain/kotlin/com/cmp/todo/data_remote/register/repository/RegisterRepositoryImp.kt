package com.cmp.todo.data_remote.register.repository

import com.cmp.todo.data_remote.login.repository.Config
import com.cmp.todo.data_remote.register.remote.api.RegisterApi
import com.cmp.todo.data_remote.register.remote.dto.RegisterRequest
import com.cmp.todo.data_remote.register.remote.dto.RegisterResponseDto
import com.cmp.todo.domain.register.entity.RegisterEntity
import com.cmp.todo.domain.register.repository.RegisterRepository
import com.cmp.todo.util.BaseResult
import com.cmp.todo.util.MockDataUtil
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class RegisterRepositoryImp(
    private val registerApi: RegisterApi
) : RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, RegisterResponseDto>> {
        return flow {
            val response = if (Config.ENVIRONMENT == "DEV") {
                MockDataUtil.getMockRegisterResultData().data
            } else {
                registerApi.register(registerRequest).body<RegisterResponseDto>()
            }
            response?.data.let { registerResponse ->
                registerResponse?.let {
                    val registerEntity = RegisterEntity(
                        it.id,
                        it.name,
                        it.email,
                        it.token
                    )
                    emit(BaseResult.Success(registerEntity))
                }
            }
            runCatching {
                val errorBody = response?.message!!
                val errorResponse: RegisterResponseDto = Json.decodeFromString(errorBody)
                errorResponse.let { error ->
                    error.code = response.code
                    emit(BaseResult.Error(error))
                }
            }.onFailure { exception ->
                emit(
                    BaseResult.Error(
                        RegisterResponseDto(
                            message = exception.message
                        )
                    )
                )
            }
        }
    }
}