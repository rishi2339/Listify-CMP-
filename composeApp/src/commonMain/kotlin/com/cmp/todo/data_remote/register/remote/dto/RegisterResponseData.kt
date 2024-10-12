package com.cmp.todo.data_remote.register.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseData(
    @SerialName("Id") var id: String,
    @SerialName("Name") var name: String,
    @SerialName("Email") var email: String,
    @SerialName("Token") var token: String
)
