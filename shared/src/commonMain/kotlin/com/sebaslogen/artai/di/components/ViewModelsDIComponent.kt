package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.presentation.SDUIScreenComponentViewModel
import kotlin.coroutines.CoroutineContext

/**
 * Provides Decompose Components
 */
interface ViewModelsDIComponent {
    /**
     * Inject an [ActionHandlerSync] manually providing the [Navigator] at injection time
     */
    val actionHandlerSyncCreator: (navigator: Navigator) -> ActionHandlerSync

    /**
     * Inject a [SDUIScreenComponentViewModel] manually providing some dependencies at injection time
     */
    val theSDUIViewModelCreator:
                (coroutineContext: CoroutineContext, actionHandler: ActionHandlerSync, url: Url) -> SDUIScreenComponentViewModel
}