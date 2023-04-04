package com.sebaslogen.artai.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sebaslogen.artai.Greeting
import com.sebaslogen.artai.PlatformGreeter
import com.sebaslogen.artai.android.ui.components.ScreenContent
import com.sebaslogen.artai.domain.DynamicUIViewModel
import com.sebaslogen.artai.domain.DynamicUIViewState
import io.github.aakira.napier.Napier
import me.tatarka.inject.annotations.Inject


typealias HomeScreen = @Composable (PlatformGreeter) -> Unit

@Inject
@Composable
fun HomeScreen(dynamicUiViewModel: () -> DynamicUIViewModel, platformGreeter: PlatformGreeter) {
    val viewModel = viewModel { dynamicUiViewModel() }
    LaunchedEffect(Unit) {// Vanilla suspend call to fetch something from network
        val home = viewModel.getHome()
        val screen = home.screen
        Napier.d { "ViewModel Response was: $screen with id: ${screen.id}" }
    }
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()

    Column {
        val platformGreet = remember { Greeting().greet() }
        GreetingView(platformGreet)
        val injectedGreet = remember { platformGreeter.greet() }
        GreetingView(injectedGreet)
        Spacer(modifier = Modifier.height(20.dp))
        when (val state = viewState.value) {
            is DynamicUIViewState.Error -> Text("Error loading data :(")
            DynamicUIViewState.Loading -> Text("Loading...")
            is DynamicUIViewState.Success -> ScreenContent(state.data.screen)
        }
    }
}