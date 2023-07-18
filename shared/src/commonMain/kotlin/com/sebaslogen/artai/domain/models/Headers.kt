package com.sebaslogen.artai.domain.models

sealed class SectionHeader {
    abstract val id: String?


    data class Large(
        override val id: String,
        val title: String,
    ) : SectionHeader()


    data class Normal(
        override val id: String,
        val title: String,
    ) : SectionHeader()


    data class SmallArt(
        override val id: String,
        val image: String,
        val title: String,
        val subtitle: String,
    ) : SectionHeader()


    data class Unknown(val type: String, override val id: String? = null) : SectionHeader()
}
