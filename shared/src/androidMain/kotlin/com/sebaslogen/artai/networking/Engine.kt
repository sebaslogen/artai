package com.sebaslogen.artai.networking

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig

actual val Engine: HttpClientEngineFactory<HttpClientEngineConfig>
    get() = OkHttp

actual fun HttpClientConfig<HttpClientEngineConfig>.configurePlatform() {
    @Suppress("UNCHECKED_CAST")
    (this as HttpClientConfig<OkHttpConfig>).realConfigurePlatform()
}

private fun HttpClientConfig<OkHttpConfig>.realConfigurePlatform() {}