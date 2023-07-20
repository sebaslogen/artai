package com.sebaslogen.artai.domain.models

import kotlin.native.ObjCName

data class Screen(
    val id: String,
    val sections: List<Section>
)

@ObjCName(swiftName = "KMPSection") // Use this name to avoid iOS name collision with class Section
sealed class Section {
    abstract val id: String?

    data class Carousel(
        override val id: String,
        val header: SectionHeader,
        val style: CarouselStyle,
        val items: List<CarouselItem>
    ) : Section() {
        enum class CarouselStyle {
            Squared, Circle, RoundedSquares
        }
    }

    data class Footer(
        override val id: String,
        val text: String
    ) : Section()

    data class ListSection(
        override val id: String,
        val header: SectionHeader,
        val items: List<ListItem>
    ) : Section()

    data class Unknown(val type: String, override val id: String? = null) : Section()
}
