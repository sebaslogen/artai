package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.Action

interface ActionHandler {
    fun onAction(action: Action)
}
