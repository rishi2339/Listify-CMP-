package com.cmp.todo.data_remote.register.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    @SerialName("data") var data: RegisterResponseData? = null,
    @SerialName("message") var message: String? = null,
    @SerialName( "code") var code: String? = null,
)
