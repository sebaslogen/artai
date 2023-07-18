package com.sebaslogen.artai.domain

interface EventHandler {
    fun onEvent(event: DynamicUIViewEvent)
}