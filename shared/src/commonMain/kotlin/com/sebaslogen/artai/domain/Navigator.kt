package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.Action

interface Navigator {
    fun onNavigation(action: Action.OpenScreen)
}