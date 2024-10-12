package com.cmp.todo.data_remote.register.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("name") var name: String = "",
    @SerialName("email") var email: String = "",
    @SerialName("password") var password: String = ""
)