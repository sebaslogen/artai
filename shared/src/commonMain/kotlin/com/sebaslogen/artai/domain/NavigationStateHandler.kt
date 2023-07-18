package com.sebaslogen.artai.domain

interface NavigationStateHandler {
    fun onNavigationStateUpdate(event: DynamicUINavigationState)
}