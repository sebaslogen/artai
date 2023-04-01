package com.sebaslogen.artai.networking

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

typealias Http = HttpClient

expect val Engine: HttpClientEngineFactory<HttpClientEngineConfig>

expect fun HttpClientConfig<HttpClientEngineConfig>.configurePlatform()

fun httpClient(): Http {
    return HttpClient(Engine) {
        configurePlatform()

        expectSuccess = true

        install(HttpTimeout) {
            connectTimeoutMillis = 30.seconds.inWholeMilliseconds
            requestTimeoutMillis = 30.seconds.inWholeMilliseconds
            socketTimeoutMillis = 2.minutes.inWholeMilliseconds
        }
        install(ContentNegotiation) {
            json(Json {
                isLenient = false
                prettyPrint = true // Useful for debugging
                ignoreUnknownKeys = true
            })
        }
        install(WebSockets)
        install(Logging) {
//            level = if (BuildKonfig.DEBUG) { // TODO: Use BuildKonfig
//                LogLevel.HEADERS
//            } else {
//                LogLevel.INFO
//            }
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.i(tag = "HttpClient") { message }
                }
            }
        }
    }
}
