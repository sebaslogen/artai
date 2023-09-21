package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.presentation.SDUIScreenComponentViewModel
import kotlin.coroutines.CoroutineContext

/**
 * Provides Decompose Components
 */
interface ViewModelsDIComponent {

    /**
     * Inject a [SDUIScreenComponentViewModel] manually providing some dependencies at injection time
     */
    val theSDUIViewModelCreator:
                (coroutineContext: CoroutineContext, navigator: Navigator, url: Url) -> SDUIScreenComponentViewModel
}