package com.sebaslogen.artai.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.sebaslogen.artai.android.di.components.androidApplicationDIComponent
import com.sebaslogen.artai.android.ui.RootScreen
import com.seiko.imageloader.LocalImageLoader
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Napier.d { "Starting MainActivity. Debug build: ${BuildConfig.DEBUG}" }

        val mainActivityDIComponent = MainActivityDIComponent::class.create(
            parent = androidApplicationDIComponent,
            componentContext = defaultComponentContext()
        )
        val rootComponent = mainActivityDIComponent.rootComponent

        setContent {
            CompositionLocalProvider(
                LocalImageLoader provides mainActivityDIComponent.imageLoaderCreator()
            ) {
                MyApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        RootScreen(rootComponent)
                    }
                }
            }
        }
    }
}