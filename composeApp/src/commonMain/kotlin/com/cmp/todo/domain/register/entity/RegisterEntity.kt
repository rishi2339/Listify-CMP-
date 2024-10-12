package com.cmp.todo.domain.register.entity

data class RegisterEntity(
    val id: String,
    val name: String,
    val email: String,
    val token: String
)