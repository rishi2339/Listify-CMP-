package com.cmp.todo.domain.login.entity

data class LoginEntity(
    var id: String,
    var name: String,
    var email: String,
    var token: String
)