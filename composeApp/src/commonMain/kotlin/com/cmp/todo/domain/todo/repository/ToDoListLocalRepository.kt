package com.cmp.todo.domain.todo.repository

import com.cmp.todo.domain.todo.entity.ToDo
import com.cmp.todo.domain.todo.entity.ToDoEntity

interface ToDoListLocalRepository{
    suspend fun getAllToDos(): ToDoEntity
    suspend fun deleteAllToDos()
    suspend fun addToDo(todos: ToDoEntity)
    suspend fun insertToDo(toDo: ToDo)
}