package com.sebaslogen.artai.domain

import io.github.aakira.napier.Napier

interface NavigationStateHandler {
    fun onNavigationStateUpdate(event: DynamicUINavigationState)
}

abstract class NotANavigationStateHandler(private val name: String) : NavigationStateHandler {
    override fun onNavigationStateUpdate(event: DynamicUINavigationState) {
        Napier.e {
            "Class $name cannot handle navigation events. Received event: $event"
        }
    }
}