package com.sebaslogen.artai.presentation

import com.sebaslogen.artai.domain.models.Screen

sealed class DynamicUIViewState {
    data object Loading : DynamicUIViewState()
    data class Error(val error: Throwable) : DynamicUIViewState()
    data class Success(val data: Screen) : DynamicUIViewState()
}