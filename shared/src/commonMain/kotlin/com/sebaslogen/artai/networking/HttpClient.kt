package com.sebaslogen.artai.networking

import com.sebaslogen.artai.data.remote.models.ApiCarouselItem
import com.sebaslogen.artai.data.remote.models.ApiListItem
import com.sebaslogen.artai.data.remote.models.ApiSection
import com.sebaslogen.artai.data.remote.models.ApiSectionHeader
import com.sebaslogen.artai.shared.build.BuildKonfig
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.HttpClientCall
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import io.ktor.util.date.GMTDate
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.plus
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

typealias Http = HttpClient

expect val Engine: HttpClientEngineFactory<HttpClientEngineConfig>

expect fun HttpClientConfig<HttpClientEngineConfig>.configurePlatform()

fun httpClient(): Http =
    HttpClient(Engine) {
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
                classDiscriminator = "type"
                serializersModule = ApiSection.serializers + ApiSectionHeader.serializers + ApiCarouselItem.serializers + ApiListItem.serializers
            })
        }
        install(Logging) {
            level = if (BuildKonfig.DEBUG) {
                LogLevel.HEADERS
            } else {
                LogLevel.INFO
            }
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.i(tag = "HttpClient") { message }
                }
            }
        }
    }
        .addGitHubRawTextContentTypeToJsonInterceptor()


// TODO: Remove this interceptor when the backend fixes the content type - Backend is just RAW text file in GitHub
@OptIn(InternalAPI::class)
private fun HttpClient.addGitHubRawTextContentTypeToJsonInterceptor(): HttpClient = apply {
    receivePipeline.intercept(HttpReceivePipeline.Before) { response ->
        this.proceedWith(object : HttpResponse() {
            override val call: HttpClientCall = response.call
            override val content: ByteReadChannel = response.content
            override val coroutineContext: CoroutineContext = response.coroutineContext
            override val headers: Headers = HeadersBuilder().apply {
                appendAll(response.headers)
                this.remove(HttpHeaders.ContentType)
                this.append(HttpHeaders.ContentType, "application/json")
            }.build()
            override val requestTime: GMTDate = response.requestTime
            override val responseTime: GMTDate = response.responseTime
            override val status: HttpStatusCode = response.status
            override val version: HttpProtocolVersion = response.version
        })
    }
}
