package com.sebaslogen.artai.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.sebaslogen.artai.android.di.components.ApplicationComponent
import com.sebaslogen.artai.android.di.components.applicationComponent
import com.sebaslogen.artai.android.ui.MainScreen
import com.sebaslogen.artai.android.ui.components.FavoriteContainer
import com.sebaslogen.artai.android.ui.components.LocalFavoriteContainer
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.presentation.DynamicUIViewModel
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import me.tatarka.inject.annotations.Component

@Component
abstract class MainActivityComponent(@Component val parent: ApplicationComponent) {
    abstract val navigationStateCreator: () -> MutableStateFlow<DynamicUINavigationState>
    abstract val dynamicUIRepositoryCreator: () -> DynamicUIRepository
    abstract val imageLoaderCreator: () -> ImageLoader
    abstract val dynamicUIViewModel: DynamicUIViewModel
    abstract val mainScreen: MainScreen
    abstract val favoriteContainer: FavoriteContainer
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.d { "Starting MainActivity. Debug build: ${BuildConfig.DEBUG}" }
        val mainActivityComponent = MainActivityComponent::class.create(applicationComponent)

        val navigationState: MutableStateFlow<DynamicUINavigationState> = mainActivityComponent.navigationStateCreator()
        val mainScreen = mainActivityComponent.mainScreen
        setContent {
            CompositionLocalProvider(
                LocalImageLoader provides mainActivityComponent.imageLoaderCreator(),
                LocalFavoriteContainer provides mainActivityComponent.favoriteContainer,
            ) {
                MyApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        mainScreen(navigationState)
                    }
                }
            }
        }
    }
}