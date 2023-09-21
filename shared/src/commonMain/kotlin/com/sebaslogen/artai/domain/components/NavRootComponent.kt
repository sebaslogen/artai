package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.sebaslogen.artai.di.components.AppComponentsDIComponent
import com.sebaslogen.artai.di.scopes.MainActivityScope
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.components.RootComponent.Child
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.Url
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Root component of the application that initializes
 * lifecycles, state restoration, instance keepers, back handlers and business logic.
 */
@MainActivityScope
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
        initialConfiguration = Config.HomeScreen,
        handleBackButton = true, // Automatically pop from the stack on back button presses
        childFactory = ::createChild
    )
    override val childrenStack: Value<ChildStack<*, Child>> = stack

    /**
     * Navigation configuration at app level.
     */
    @Parcelize
    private sealed interface Config : Parcelable {
        object HomeScreen : Config // TODO: Use data object if possible

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