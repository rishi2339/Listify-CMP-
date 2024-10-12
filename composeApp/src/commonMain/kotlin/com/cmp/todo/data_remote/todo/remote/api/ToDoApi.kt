package com.cmp.todo.data_remote.todo.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ToDoApi(
    private val httpClient: HttpClient
) {
    suspend fun getAllToDos(): HttpResponse {
        return httpClient.get("https://c7ba479b-8cfa-4a2e-94ad-8956099c619f.mock.pstmn.io/todos"){
            contentType(ContentType.Application.Json)
        }
    }
}