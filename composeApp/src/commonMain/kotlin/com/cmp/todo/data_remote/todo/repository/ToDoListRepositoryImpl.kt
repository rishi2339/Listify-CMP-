package com.cmp.todo.data_remote.todo.repository

import com.cmp.todo.data_remote.login.repository.Config
import com.cmp.todo.data_remote.todo.mapper.toToDo
import com.cmp.todo.data_remote.todo.remote.api.ToDoApi
import com.cmp.todo.data_remote.todo.remote.dto.ToDoResponseDto
import com.cmp.todo.domain.todo.entity.ToDo
import com.cmp.todo.domain.todo.entity.ToDoEntity
import com.cmp.todo.domain.todo.repository.ToDoListLocalRepository
import com.cmp.todo.domain.todo.repository.ToDoListRepository
import com.cmp.todo.util.BaseResult
import com.cmp.todo.util.MockDataUtil
import com.cmp.todo.util.Resource
import io.ktor.client.call.body
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToDoListRepositoryImpl(
    private val remoteDataSource: ToDoApi,
    private val localDataSource: ToDoListLocalRepository
) : ToDoListRepository {
    override suspend fun getToDos(fetchFromRemote: Boolean): Flow<BaseResult<ToDoEntity, ToDoResponseDto>> {
        return flow {
            val localListing = localDataSource.getAllToDos()
            val isDBEmpty = localListing.allTodo.isEmpty()
            val shouldLoadFromCache = !isDBEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(
                    BaseResult.Success(
                        ToDoEntity(
                            allTodo = localListing.allTodo
                        )
                    )
                )
                return@flow
            }
            val remoteListing = try {
                if (Config.ENVIRONMENT == "DEV")
                    MockDataUtil.getMockToDoResultData()
                else {
                    Resource.Success(
                        ToDoResponseDto(
                            data = remoteDataSource.getAllToDos().body()
                        )
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    BaseResult.Error(
                        ToDoResponseDto(
                            message = e.message
                        )
                    )
                )
                null
            }
            remoteListing?.data.let { todoResponse ->
                localDataSource.deleteAllToDos()
                todoResponse?.data?.let { todoList ->
                    val toDoEntity = ToDoEntity(
                        allTodo = todoList.allTodo.map { it.toToDo() }
                    )
                    localDataSource.addToDo(toDoEntity)
                    emit(BaseResult.Success(toDoEntity))
                }
            }
        }
    }

    override suspend fun addToDo(toDo: ToDo) {
        localDataSource.insertToDo(toDo)
    }
}