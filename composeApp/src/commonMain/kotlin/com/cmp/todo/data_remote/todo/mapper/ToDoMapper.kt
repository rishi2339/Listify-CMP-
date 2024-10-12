package com.cmp.todo.data_remote.todo.mapper

import com.cmp.todo.data_remote.todo.remote.dto.ToDoDto
import com.cmp.todo.domain.todo.entity.ToDo


fun ToDoDto.toToDo(): ToDo {
    return ToDo(
        title = this.title,
        userId = this.userId,
        completed = this.completed,
    )
}