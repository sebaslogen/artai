package com.sebaslogen.artai.domain

sealed class DynamicUIViewEvent {
    data class OnOpenScreen(val url: String) : DynamicUIViewEvent()
    object OnAppStart : DynamicUIViewEvent()
}
