package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.Action
import io.github.aakira.napier.Napier

interface Navigator {
    fun onNavigation(action: Action.OpenScreen)
}

abstract class NotANavigatorHandler(private val name: String) : Navigator {
    override fun onNavigation(action: Action.OpenScreen) {
        Napier.e {
            "Class $name cannot handle navigation actions. Received action: $action"
        }
    }
}