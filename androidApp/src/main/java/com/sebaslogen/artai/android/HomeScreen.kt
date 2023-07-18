package com.sebaslogen.artai.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sebaslogen.artai.Greeting
import com.sebaslogen.artai.PlatformGreeter
import com.sebaslogen.artai.android.ui.components.ScreenContent
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.presentation.DynamicUIViewModel
import com.sebaslogen.artai.presentation.DynamicUIViewState
import kotlinx.coroutines.flow.StateFlow
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


typealias MainScreen = @Composable (PlatformGreeter, StateFlow<DynamicUINavigationState>) -> Unit

@Inject
@Composable
fun MainScreen(
    dynamicUiViewModelProvider: () -> DynamicUIViewModel,
    @Assisted platformGreeter: PlatformGreeter,
    @Assisted navigationState: StateFlow<DynamicUINavigationState>
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreenContent(dynamicUiViewModelProvider, platformGreeter) }
        composable("sdui") { SDUIScreenContent(dynamicUiViewModelProvider, navigationState) }
    }

    val navState: DynamicUINavigationState by navigationState.collectAsStateWithLifecycle()
    when (navState) {
        DynamicUINavigationState.HomeScreen -> navController.navigate("home")
        is DynamicUINavigationState.RemoteScreen -> navController.navigate("sdui")
    }
}

@Composable
private fun HomeScreenContent(dynamicUiViewModelProvider: () -> DynamicUIViewModel, platformGreeter: PlatformGreeter) {
    val viewModel = viewModel { dynamicUiViewModelProvider() }
    val viewState: DynamicUIViewState by viewModel.viewState.collectAsStateWithLifecycle()

    Column {
        val platformGreet = remember { Greeting().greet() }
        GreetingView(platformGreet)
        val injectedGreet = remember { platformGreeter.greet() }
        GreetingView(injectedGreet)
        Button(onClick = viewModel::onRefreshClicked) {
            Text("Refresh")
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (val state = viewState) {
            is DynamicUIViewState.Error -> Text("Error loading data :(")
            DynamicUIViewState.Loading -> Text("Loading...")
            is DynamicUIViewState.Success -> ScreenContent(state.data, viewModel)
        }
    }
}

@Composable
private fun SDUIScreenContent(dynamicUiViewModelProvider: () -> DynamicUIViewModel, navigationState: StateFlow<DynamicUINavigationState>) {
    val viewModel = viewModel { dynamicUiViewModelProvider() }
    val viewState: DynamicUIViewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navState: DynamicUINavigationState by navigationState.collectAsStateWithLifecycle()

    Column {
        when (val e = navState) {
            DynamicUINavigationState.HomeScreen -> Text("Home")
            is DynamicUINavigationState.RemoteScreen -> Text(e.url)
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (val state = viewState) {
            is DynamicUIViewState.Error -> Text("Error loading data :(")
            DynamicUIViewState.Loading -> Text("Loading...")
            is DynamicUIViewState.Success -> ScreenContent(state.data, viewModel)
        }
    }
}