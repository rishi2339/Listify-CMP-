package com.cmp.todo.data_remote.register.remote.api

import com.cmp.todo.data_remote.register.remote.dto.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RegisterApi(
    private val httpClient: HttpClient
) {
    suspend fun register(registerRequest: RegisterRequest) : HttpResponse {
        return httpClient.post("https://c7ba479b-8cfa-4a2e-94ad-8956099c619f.mock.pstmn.io/register"){
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }
    }
}