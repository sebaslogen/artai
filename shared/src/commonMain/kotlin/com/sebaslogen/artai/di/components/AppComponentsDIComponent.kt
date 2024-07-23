package com.sebaslogen.artai.di.components

import com.arkivanov.decompose.ComponentContext
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.components.SDUIScreenComponent
import com.sebaslogen.artai.domain.models.Url
import kotlin.coroutines.CoroutineContext

/**
 * Provides Decompose Components
 */
interface AppComponentsDIComponent {

    val theSDUIScreenComponentCreator:
                (componentContext: ComponentContext, coroutineContext: CoroutineContext, navigator: Navigator, url: Url) -> SDUIScreenComponent
}