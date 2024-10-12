package com.cmp.todo.domain.todo.usecase

import com.cmp.todo.data_remote.todo.remote.dto.ToDoResponseDto
import com.cmp.todo.domain.todo.entity.ToDo
import com.cmp.todo.domain.todo.repository.ToDoListRepository
import com.cmp.todo.domain.todo.entity.ToDoEntity
import com.cmp.todo.util.BaseResult
import kotlinx.coroutines.flow.Flow

class ToDoUseCase(
    private val toDoListRepository: ToDoListRepository
) {
    suspend fun getToDoExecute(): Flow<BaseResult<ToDoEntity, ToDoResponseDto>> {
        return toDoListRepository.getToDos(false)
    }
//    suspend fun insertToDoExecute(toDo: ToDo) {
//        return toDoListRepository.addToDo(toDo)
//    }
}