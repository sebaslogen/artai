package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.presentation.SDUIScreenComponentViewModel
import kotlin.coroutines.CoroutineContext

class SDUIScreenComponent(
    componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    val url: Url
) : ComponentContext by componentContext {

    private val viewModel = instanceKeeper.getOrCreate { SDUIScreenComponentViewModel(coroutineContext = coroutineContext, url = url) }
}
