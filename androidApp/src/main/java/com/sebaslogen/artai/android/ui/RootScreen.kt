package com.sebaslogen.artai.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.sebaslogen.artai.domain.components.RootComponent
import com.sebaslogen.artai.presentation.DynamicUIViewModel

@Composable
fun RootScreen(
    component: RootComponent,
    dynamicUiViewModelProvider: () -> DynamicUIViewModel,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childrenStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.HomeScreen -> HomeScreenContent(component = child.component, dynamicUiViewModelProvider)
            is RootComponent.Child.RemoteScreen -> SDUIScreenContent(component = child.component, dynamicUiViewModelProvider)
        }
    }
}