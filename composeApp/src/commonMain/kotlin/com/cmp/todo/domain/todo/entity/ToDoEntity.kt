package com.cmp.todo.domain.todo.entity

data class ToDo(
    val title:String,
    val completed:Boolean,
    val userId:Int
)

data class ToDoEntity(
    val allTodo: List<ToDo>
)