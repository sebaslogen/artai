package com.sebaslogen.artai.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sebaslogen.artai.PlatformGreeter
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.presentation.DynamicUIViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.drop
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

    LaunchedEffect(navigationState) {
        navigationState.drop(1).collect { navState ->
            when (navState) {
                DynamicUINavigationState.HomeScreen -> navController.navigate("home")
                is DynamicUINavigationState.RemoteScreen -> navController.navigate("sdui")
            }
        }
    }
}