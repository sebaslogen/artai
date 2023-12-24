package com.sebaslogen.artai.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

fun ComponentContext.attachNewCoroutineScope(coroutineContext: CoroutineContext): CoroutineScope {
    // Create Scoped CoroutineScope
    val viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
    // Cancel CoroutineScope
    lifecycle.doOnDestroy {
        viewModelScope.cancel()
    }
    return viewModelScope
}