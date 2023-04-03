package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.Serializable


@Serializable
data class ApiScreenResponse(
    val screen: ApiScreen
)

@Serializable
data class ApiScreen(
    val id: Int
)
