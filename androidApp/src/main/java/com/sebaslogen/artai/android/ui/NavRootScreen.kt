package com.sebaslogen.artai.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.sebaslogen.artai.domain.components.RootComponent

@Composable
fun NavRootScreen(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childrenStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.HomeScreen -> HomeScreenContent(component = child.component)
            is RootComponent.Child.RemoteScreen -> SDUIScreenContent(component = child.component)
        }
    }
}