package com.cmp.todo.domain.todo.repository

import com.cmp.todo.data_remote.todo.remote.dto.ToDoResponseDto
import com.cmp.todo.domain.todo.entity.ToDo
import com.cmp.todo.domain.todo.entity.ToDoEntity
import com.cmp.todo.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface ToDoListRepository {
    suspend fun getToDos(fetchFromRemote: Boolean): Flow<BaseResult<ToDoEntity, ToDoResponseDto>>
    //suspend fun addToDo(toDo: ToDo)
}