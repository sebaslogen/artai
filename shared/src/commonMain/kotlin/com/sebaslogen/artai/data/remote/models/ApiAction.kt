package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
sealed class ApiAction {
    abstract val id: String?

    companion object {
        val serializers = SerializersModule {
            polymorphic(ApiAction::class) {
                subclass(ApiOpenScreen::class)
                defaultDeserializer { ApiUnknown.serializer() }
            }
        }
    }

    @Serializable
    @SerialName("openScreen")
    data class ApiOpenScreen(
        override val id: String,
        val url: String,
    ) : ApiAction()

    @Serializable
    @SerialName("commandAddToFavorites")
    data class ApiCommandAddToFavorites(
        override val id: String,
        override val url: String,
        override val accessibilityDescription: String,
    ) : ApiFavoriteAction, ApiAction()

    @Serializable
    @SerialName("commandRemoveFromFavorites")
    data class ApiCommandRemoveFromFavorites(
        override val id: String,
        override val url: String,
        override val accessibilityDescription: String,
    ) : ApiFavoriteAction, ApiAction()

    @Serializable
    data class ApiUnknown(val type: String, override val id: String? = null) : ApiAction()
}

interface ApiFavoriteAction {
    val id: String
    val url: String
    val accessibilityDescription: String
}