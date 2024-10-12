package com.cmp.todo.data_remote.login.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseData(
    @SerialName("Email")
    val email: String,
    @SerialName("Id")
    val id: String,
    @SerialName( "Name")
    val name: String,
    @SerialName("Token")
    val token: String
)