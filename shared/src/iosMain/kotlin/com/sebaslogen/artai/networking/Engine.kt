package com.sebaslogen.artai.networking

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

actual val Engine: HttpClientEngineFactory<HttpClientEngineConfig>
    get() = Darwin

actual fun HttpClientConfig<HttpClientEngineConfig>.configurePlatform() {
    @Suppress("UNCHECKED_CAST")
    (this as HttpClientConfig<DarwinClientEngineConfig>).realConfigurePlatform()
}

private fun HttpClientConfig<DarwinClientEngineConfig>.realConfigurePlatform() {
    engine {
        configureRequest {
        }
    }
}