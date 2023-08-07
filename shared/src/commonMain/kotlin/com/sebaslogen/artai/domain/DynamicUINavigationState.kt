package com.sebaslogen.artai.domain

sealed class DynamicUINavigationState {
    data class RemoteScreen(val url: String) : DynamicUINavigationState()
    data object HomeScreen : DynamicUINavigationState()
}
