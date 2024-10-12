package com.cmp.todo.data_remote.login.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("username")
    var email: String = "",
    @SerialName("password")
    var password: String = ""
)