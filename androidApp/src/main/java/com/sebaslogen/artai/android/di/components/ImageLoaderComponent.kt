package com.sebaslogen.artai.android.di.components

import android.content.Context
import com.sebaslogen.artai.di.scopes.Singleton
import com.sebaslogen.artai.networking.Http
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.util.DebugLogger
import me.tatarka.inject.annotations.Provides
import okio.Path.Companion.toOkioPath

interface ImageLoaderComponent {
    @Singleton
    @Provides
    fun imageLoader(applicationContext: Context, httpClientProvider: () -> Http): ImageLoader = ImageLoader {
        logger = DebugLogger()
        components {
            setupDefaultComponents(context = applicationContext, httpClient = httpClientProvider)
        }
        interceptor {
            memoryCacheConfig {
                // Set the max size to 25% of the app's available memory.
                maxSizePercent(applicationContext, 0.25)
            }
            diskCacheConfig {
                directory(applicationContext.cacheDir.resolve("image_cache").toOkioPath())
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}