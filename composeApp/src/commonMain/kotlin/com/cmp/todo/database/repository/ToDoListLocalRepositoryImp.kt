package com.cmp.todo.database.repository

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.cmp.todo.database.DBHelper
import com.cmp.todo.domain.todo.entity.ToDo
import com.cmp.todo.domain.todo.entity.ToDoEntity
import com.cmp.todo.domain.todo.repository.ToDoListLocalRepository

class ToDoListLocalRepositoryImp(private val dbHelper: DBHelper) :
    ToDoListLocalRepository {
    override suspend fun deleteAllToDos() {
        dbHelper.invoke { appDatabase ->
            appDatabase.appDatabaseQueries.deleteAllToDo()
        }
    }

    override suspend fun getAllToDos(): ToDoEntity =
        dbHelper.invoke { appDatabase ->
            ToDoEntity(
                appDatabase.appDatabaseQueries.selectAllToDo(::mapper).awaitAsList()
            )
        }

    private fun mapper(
        id: Long,
        title: String,
        completed: Long?,
        userId: Long,
    ): ToDo {
        return ToDo(
            title = title,
            completed = completed == 0L,
            userId = userId.toInt()
        )
    }

    override suspend fun addToDo(todos: ToDoEntity) =
        dbHelper.invoke {
            todos.allTodo.forEach { toDo ->
                insertToDo(toDo)
            }
        }

    override suspend fun insertToDo(toDo: ToDo) =
        dbHelper.invoke { appDatabase ->
            appDatabase.appDatabaseQueries.insertToDo(
                completed = if (toDo.completed) 1L else 0L,
                userId = toDo.userId.toLong(),
                title = toDo.title
            )
        }
}