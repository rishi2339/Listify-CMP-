package com.cmp.todo.util

import com.cmp.todo.data_remote.login.remote.dto.LoginResponseDto
import com.cmp.todo.data_remote.register.remote.dto.RegisterResponseDto
import com.cmp.todo.data_remote.todo.remote.dto.ToDoResponseDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import listify.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

object MockDataUtil {

    private const val MOCK_DELAY = 500L
    private const val MOCK_LOGIN_RESULT_FILENAME = "files/login_api_response.json"
    private const val MOCK_TODO_RESULT_FILENAME = "files/todo_result.json"
    private const val MOCK_REGISTER_RESULT_FILENAME = "files/register_api_response.json"
    @OptIn(ExperimentalResourceApi::class)
    suspend fun getMockLoginResultData(): Resource<LoginResponseDto> {
        delay(MOCK_DELAY)
        return try {
            val bytes = coroutineScope {
                val deferredBytes = async {
                    Res.readBytes(MOCK_LOGIN_RESULT_FILENAME)
                }
                deferredBytes.await()
            }
            val response = Json {
                ignoreUnknownKeys = true
            }.decodeFromString<LoginResponseDto>(bytes.decodeToString())
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Failed to parse JSON data")
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getMockRegisterResultData(): Resource<RegisterResponseDto> {
        delay(MOCK_DELAY)
        return try {
            val bytes = coroutineScope {
                val deferredBytes = async {
                    Res.readBytes(MOCK_REGISTER_RESULT_FILENAME)
                }
                deferredBytes.await()
            }
            val response = Json {
                ignoreUnknownKeys = true
            }.decodeFromString<RegisterResponseDto>(bytes.decodeToString())
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Failed to parse JSON data")
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getMockToDoResultData(): Resource<ToDoResponseDto> {
        delay(MOCK_DELAY)
        return try {
            val bytes = coroutineScope {
                val deferredBytes = async {
                    Res.readBytes(MOCK_TODO_RESULT_FILENAME)
                }
                deferredBytes.await()
            }
            val response = Json {
                ignoreUnknownKeys = true
            }.decodeFromString<ToDoResponseDto>(bytes.decodeToString())
            Resource.Success(response)

        } catch (e: Exception) {
            Resource.Error("Failed to parse JSON data")
        }
    }
}