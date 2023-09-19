package com.sebaslogen.artai.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.sebaslogen.artai.android.di.components.ApplicationComponent
import com.sebaslogen.artai.android.di.components.applicationComponent
import com.sebaslogen.artai.android.ui.HomeScreen
import com.sebaslogen.artai.android.ui.MainScreen
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.domain.components.DefaultRootComponent
import com.sebaslogen.artai.domain.components.RootComponent
import com.sebaslogen.artai.presentation.DynamicUIViewModel
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class MainActivityComponent(@Component val parent: ApplicationComponent, private val defaultComponentContext: DefaultComponentContext) {
    abstract val navigationStateCreator: () -> MutableStateFlow<DynamicUINavigationState>
    abstract val dynamicUIRepositoryCreator: () -> DynamicUIRepository
    abstract val imageLoaderCreator: () -> ImageLoader
    abstract val dynamicUIViewModel: DynamicUIViewModel
    abstract val mainScreen: MainScreen

    @Provides
    fun rootComponent(): RootComponent =
        DefaultRootComponent(componentContext = defaultComponentContext)
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.d { "Starting MainActivity. Debug build: ${BuildConfig.DEBUG}" }
        val mainActivityComponent = MainActivityComponent::class.create(applicationComponent, defaultComponentContext())

        val navigationState: MutableStateFlow<DynamicUINavigationState> = mainActivityComponent.navigationStateCreator()
        val mainScreen = mainActivityComponent.mainScreen
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
//                        mainScreen(navigationState)
                        HomeScreen(rootComponent, { mainActivityComponent.dynamicUIViewModel }, navigationState)
                    }
                }
            }
        }
    }
}