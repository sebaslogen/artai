package com.sebaslogen.artai.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.sebaslogen.artai.android.di.components.ApplicationComponent
import com.sebaslogen.artai.android.di.components.applicationComponent
import com.sebaslogen.artai.android.ui.RootScreen
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.di.scopes.MainActivityScope
import com.sebaslogen.artai.di.scopes.Singleton
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.components.DefaultRootComponent
import com.sebaslogen.artai.domain.components.RootComponent
import com.sebaslogen.artai.presentation.DynamicUIViewModel
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import io.github.aakira.napier.Napier
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@MainActivityScope
abstract class MainActivityComponent(
    @Component val parent: ApplicationComponent,
    @get:Provides protected val componentContext: ComponentContext
) {
    abstract val dynamicUIRepositoryCreator: () -> DynamicUIRepository
    abstract val imageLoaderCreator: () -> ImageLoader
    abstract val dynamicUIViewModel: DynamicUIViewModel
    abstract val defaultRootComponent: DefaultRootComponent

    @Provides
    fun rootComponent(): RootComponent = defaultRootComponent

    @Provides
    fun navigator(): Navigator = defaultRootComponent
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.d { "Starting MainActivity. Debug build: ${BuildConfig.DEBUG}" }
        val mainActivityComponent = MainActivityComponent::class.create(applicationComponent, defaultComponentContext())

        val rootComponent = mainActivityComponent.rootComponent()
        setContent {
            CompositionLocalProvider(
                LocalImageLoader provides mainActivityComponent.imageLoaderCreator(),
            ) {
                MyApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        RootScreen(rootComponent, { mainActivityComponent.dynamicUIViewModel })
                    }
                }
            }
        }
    }
}