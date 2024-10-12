package com.cmp.todo.data_remote.todo.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToDoDto(
    @SerialName("todo")
    val title:String,
    @SerialName("completed")
    val completed:Boolean,
    @SerialName("userId")
    val userId:Int
)
@Serializable
data class ToDoListDto(
    @SerialName("todos")
    val allTodo: List<ToDoDto>
)

@Serializable
data class ToDoResponseDto(
    @SerialName( "data") var data: ToDoListDto? = null,
    @SerialName( "code") var statusCode: String? = null,
    @SerialName("message") var message: String? = null,
)
