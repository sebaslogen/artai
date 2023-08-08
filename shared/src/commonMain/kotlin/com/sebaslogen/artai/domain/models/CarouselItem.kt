package com.sebaslogen.artai.domain.models

sealed class CarouselItem {

    abstract val id: String?

    data class SmallArt(
        override val id: String,
        val image: String,
        val favorite: Favorite,
        val action: Action,
    ) : CarouselItem()

    data class Unknown(val type: String, override val id: String? = null) : CarouselItem()
}