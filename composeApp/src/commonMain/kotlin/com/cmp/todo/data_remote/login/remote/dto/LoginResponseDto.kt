package com.cmp.todo.data_remote.login.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("data") var data: LoginResponseData? = null,
    @SerialName("message") var message: String? = null,
    @SerialName( "code") var code: String? = null,
)

