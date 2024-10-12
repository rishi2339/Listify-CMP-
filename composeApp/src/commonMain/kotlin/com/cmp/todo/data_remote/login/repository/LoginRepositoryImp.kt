package com.cmp.todo.data_remote.login.repository

import com.cmp.todo.data_remote.login.remote.api.LoginApi
import com.cmp.todo.data_remote.login.remote.dto.LoginRequest
import com.cmp.todo.data_remote.login.remote.dto.LoginResponseDto
import com.cmp.todo.domain.login.entity.LoginEntity
import com.cmp.todo.domain.login.repository.LoginRepository
import com.cmp.todo.util.BaseResult
import com.cmp.todo.util.MockDataUtil
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class LoginRepositoryImp(
    private val loginApi: LoginApi,
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponseDto>> {
        return flow {
            val response = if (Config.ENVIRONMENT == "DEV") {
                MockDataUtil.getMockLoginResultData().data
            } else {
                loginApi.login(loginRequest).body<LoginResponseDto>()
            }
            response?.data.let { loginResponse ->
                loginResponse?.let {
                    val loginEntity = LoginEntity(
                        it.id, it.name, it.email, it.token
                    )
                    emit(BaseResult.Success(loginEntity))
                }
            }
            runCatching {
                val errorBody = response?.message!!
                val errorResponse: LoginResponseDto = Json.decodeFromString(errorBody)
                errorResponse.let { error ->
                    error.code = response.code
                    emit(BaseResult.Error(error))
                }
            }.onFailure { exception ->
                emit(
                    BaseResult.Error(
                        LoginResponseDto(
                            message = exception.message
                        )
                    )
                )
            }
        }
    }
}