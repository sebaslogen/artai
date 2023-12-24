package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.sebaslogen.artai.di.components.AppComponentsDIComponent
import com.sebaslogen.artai.di.scopes.RootScreenScope
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.components.RootComponent.Child
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.Url
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Root component of the application that initializes
 * lifecycles, state restoration, instance keepers, back handlers and business logic.
 */
@RootScreenScope
@Inject
class NavRootComponent(
    componentContext: ComponentContext,
    private val mainCoroutineContext: CoroutineContext,
    private val appComponentsDIComponent: AppComponentsDIComponent
) : RootComponent, ComponentContext by componentContext, Navigator {
//    This class has (thanks to the ComponentContext) access to
//        lifecycle... // Access the Lifecycle
//        stateKeeper... // Access the StateKeeper
//        instanceKeeper... // Access the InstanceKeeper
//        backHandler... // Access the BackHandler

    private val navigation = StackNavigation<Config>()
    private val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.HomeScreen,
        handleBackButton = true, // Automatically pop from the stack on back button presses
        childFactory = ::createChild
    )
    override val childrenStack: Value<ChildStack<*, Child>> = stack

    /**
     * Navigation configuration at app level.
     */
    @Serializable
    private sealed interface Config {
        @Serializable
        data object HomeScreen : Config

        @Serializable
        data class RemoteScreen(val url: Url) : Config
    }

    private fun createChild(config: Config, componentContext: ComponentContext): Child {
        return when (config) {
            // TODO: Assisted injection
            is Config.HomeScreen ->
                Child.HomeScreen(
                    appComponentsDIComponent.theSDUIScreenComponentCreator(componentContext, mainCoroutineContext, this, Url(""))
                )
            is Config.RemoteScreen ->
                Child.RemoteScreen(
                    appComponentsDIComponent.theSDUIScreenComponentCreator(componentContext, mainCoroutineContext, this, config.url)
                )
        }
    }

    override fun onNavigation(action: Action.OpenScreen) {
        navigation.push(Config.RemoteScreen(url = action.url))
    }
}