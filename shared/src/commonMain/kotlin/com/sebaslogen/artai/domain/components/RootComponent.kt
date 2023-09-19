package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val childrenStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class HomeScreen(val component: HomeScreenComponent) : Child()
        class RemoteScreen(val component: RemoteScreenComponent) : Child()
    }
}