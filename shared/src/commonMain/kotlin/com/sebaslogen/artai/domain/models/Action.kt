package com.sebaslogen.artai.domain.models

sealed class Action {
    abstract val id: String?

    data class OpenScreen(
        override val id: String,
        val url: String,
    ) : Action()

    data class Unknown(val type: String, override val id: String? = null) : Action()
}