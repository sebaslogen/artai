package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import com.sebaslogen.artai.presentation.SDUIScreenComponentViewModel
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext

@Inject
class SDUIScreenComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val coroutineContext: CoroutineContext,
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    @Assisted private val actionHandler: ActionHandlerSync,
    @Assisted val url: Url
) : ComponentContext by componentContext {

    val viewModel = instanceKeeper.getOrCreate {
        SDUIScreenComponentViewModel(
            coroutineContext = coroutineContext,
            dynamicUIUseCase = dynamicUIUseCase,
            favoritesUseCase = favoritesUseCase,
            actionHandler = actionHandler,
            url = url
        )
    }
}
