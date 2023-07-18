package com.sebaslogen.artai.domain

import com.sebaslogen.artai.data.remote.models.ApiAction

interface ActionHandler {
    fun onAction(action: ApiAction): Unit
}