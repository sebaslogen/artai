package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.sebaslogen.artai.di.components.ViewModelsDIComponent
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.models.Url
import kotlin.coroutines.CoroutineContext

class SDUIScreenComponent(
    private val componentContext: ComponentContext,
    viewModelsDIComponent: ViewModelsDIComponent,
    private val coroutineContext: CoroutineContext,
    private val newNavigator: Navigator,
    val url: Url, // TODO: Make private
) : ComponentContext by componentContext {

    val viewModel = instanceKeeper.getOrCreate {
        viewModelsDIComponent.theSDUIViewModelCreator(
            coroutineContext,
            newNavigator,
            url
        )
    }.apply { // Update Navigator when Component is recreated (e.g. config change)
        navigator = newNavigator
    }
}
