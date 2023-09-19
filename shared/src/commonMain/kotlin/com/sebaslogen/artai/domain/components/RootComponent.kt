package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.sebaslogen.artai.domain.Navigator

interface RootComponent: Navigator {

    val childrenStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class HomeScreen(val component: HomeScreenComponent) : Child()
        class RemoteScreen(val component: SDUIScreenComponent) : Child()
    }
}