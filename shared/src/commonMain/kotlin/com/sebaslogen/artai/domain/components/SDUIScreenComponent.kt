package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.sebaslogen.artai.di.components.ViewModelsDIComponent
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import com.sebaslogen.artai.presentation.SDUIScreenComponentViewModel
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext

class SDUIScreenComponent(
    private val componentContext: ComponentContext,
    viewModelsDIComponent: ViewModelsDIComponent,
    private val coroutineContext: CoroutineContext,
    private val actionHandler: ActionHandlerSync,
    val url: Url, // TODO: Make private
) : ComponentContext by componentContext {

    val viewModel = instanceKeeper.getOrCreate {
        viewModelsDIComponent.theSDUIViewModelCreator(
            coroutineContext,
            actionHandler,
            url
        )
    }
}
