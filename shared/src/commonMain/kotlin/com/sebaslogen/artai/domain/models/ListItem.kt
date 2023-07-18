package com.sebaslogen.artai.domain.models

sealed class ListItem {

    abstract val id: String?
    
    data class BigArt(
        override val id: String,
        val title: String,
        val image: String,
    ) : ListItem()

    data class Unknown(val type: String, override val id: String? = null) : ListItem()
}