package com.sebaslogen.artai.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.sebaslogen.artai.android.ui.components.ScreenContent
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.domain.components.RootComponent
import com.sebaslogen.artai.presentation.DynamicUIViewModel
import com.sebaslogen.artai.presentation.DynamicUIViewState
import kotlinx.coroutines.flow.StateFlow


@Composable
fun HomeScreen(
    component: RootComponent,
    dynamicUiViewModelProvider: () -> DynamicUIViewModel,
    navigationState: StateFlow<DynamicUINavigationState>,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childrenStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.HomeScreen -> HomeScreenContent(dynamicUiViewModelProvider)
            is RootComponent.Child.RemoteScreen -> SDUIScreenContent(dynamicUiViewModelProvider, navigationState)
        }
    }
}

/**
 * Home screen with manual refresh button hardcoded on screen and SDUI content below
 */
@Composable
fun HomeScreenContent(dynamicUiViewModelProvider: () -> DynamicUIViewModel) {
    val viewModel = viewModel { dynamicUiViewModelProvider() }
    val viewState: DynamicUIViewState by viewModel.viewState.collectAsStateWithLifecycle()

    Column {
        Text("Home Screen content")
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
fun SDUIScreenContent(
    dynamicUiViewModelProvider: () -> DynamicUIViewModel,
    navigationState: StateFlow<DynamicUINavigationState>
) {
    val viewModel: DynamicUIViewModel = viewModel { dynamicUiViewModelProvider() }
    val viewState: DynamicUIViewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navState: DynamicUINavigationState by navigationState.collectAsStateWithLifecycle()

    Column {
        when (val e = navState) {
            DynamicUINavigationState.HomeScreen -> Text("Home")
            is DynamicUINavigationState.RemoteScreen -> Text("Remote url is ...${e.url.takeLast(20)}")
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (val state = viewState) {
            is DynamicUIViewState.Error -> Text("Error loading data :(")
            DynamicUIViewState.Loading -> Text("Loading...")
            is DynamicUIViewState.Success -> ScreenContent(state.data, viewModel)
        }
    }
}