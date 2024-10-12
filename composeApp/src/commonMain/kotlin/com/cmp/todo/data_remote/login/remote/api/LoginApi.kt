package com.cmp.todo.data_remote.login.remote.api

import androidx.compose.runtime.rememberCoroutineScope
import com.cmp.todo.data_remote.login.remote.dto.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoginApi(
    private val httpClient: HttpClient
) {
    suspend fun login(loginRequest: LoginRequest): HttpResponse {
        val resp = httpClient.post("https://c7ba479b-8cfa-4a2e-94ad-8956099c619f.mock.pstmn.io/login"){
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }
        return resp
    }
}