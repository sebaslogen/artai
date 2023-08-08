package com.sebaslogen.artai.domain.models

data class Favorite(
    val id: String,
    val favorited: Boolean,
    val toggleFavoriteState: Action.ToggleFavoriteState
)