package com.sebaslogen.artai.di.components

import com.arkivanov.decompose.ComponentContext
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.components.HomeScreenComponent
import com.sebaslogen.artai.domain.components.SDUIScreenComponent
import com.sebaslogen.artai.domain.models.Url
import kotlin.coroutines.CoroutineContext

/**
 * Provides Decompose Components
 */
interface AppComponentsDIComponent {
    val actionHandlerSyncCreator: (navigator: Navigator) -> ActionHandlerSync

    val theSDUIScreenComponentCreator:
                (componentContext: ComponentContext, coroutineContext: CoroutineContext, actionHandler: ActionHandlerSync, url: Url) -> SDUIScreenComponent
    val homeScreenComponentCreator:
                (componentContext: ComponentContext, coroutineContext: CoroutineContext, actionHandler: ActionHandlerSync, url: Url) -> HomeScreenComponent
}