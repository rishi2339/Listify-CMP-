package com.cmp.todo.di.module


import com.cmp.todo.presentation.settings.SettingsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.AttributeKey
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val providehttpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = false
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
            install(RequestInterceptor(get()))
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }
}
internal class RequestInterceptor(private val tokenProvider: SettingsRepository) : HttpClientPlugin<Unit, RequestInterceptor> {

    override val key: AttributeKey<RequestInterceptor> = AttributeKey("RequestInterceptor")
    override fun install(plugin: RequestInterceptor, scope: HttpClient) {
        scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
            val token = plugin.tokenProvider.getToken()
            context.headers.append("Authorization", token)
            proceed()
        }
    }
    override fun prepare(block: Unit.() -> Unit): RequestInterceptor {
        return this
    }
}
