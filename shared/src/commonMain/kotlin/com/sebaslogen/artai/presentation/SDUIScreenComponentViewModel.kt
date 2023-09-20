package com.sebaslogen.artai.presentation

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.sebaslogen.artai.domain.models.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class SDUIScreenComponentViewModel(
    coroutineContext: CoroutineContext,
    private val url: Url
) : InstanceKeeper.Instance {
    private val viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())

    override fun onDestroy() {
        viewModelScope.cancel()
    }
}