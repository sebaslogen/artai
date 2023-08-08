package com.sebaslogen.artai.domain.models

import com.sebaslogen.artai.data.remote.models.ApiAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Action {
    abstract val id: String?

    data class OpenScreen(
        override val id: String,
        val url: String,
    ) : Action()

    data class ToggleFavoriteState(
        override val id: String,
        val url: String
    ) : Action()

    data class Unknown(val type: String, override val id: String? = null) : Action()
}