package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
sealed class ApiSectionHeader {

    abstract val id: String?

    companion object {
        val serializers = SerializersModule {
            polymorphic(ApiSectionHeader::class) {
                subclass(ApiLarge::class)
                subclass(ApiNormal::class)
                subclass(ApiSmallArt::class)
                defaultDeserializer { ApiUnknown.serializer() }
            }
        }
    }

    @Serializable
    @SerialName("large")
    data class ApiLarge(
        override val id: String,
        val title: String,
    ) : ApiSectionHeader()

    @Serializable
    @SerialName("normal")
    data class ApiNormal(
        override val id: String,
        val title: String,
    ) : ApiSectionHeader()

    @Serializable
    @SerialName("smallArt")
    data class ApiSmallArt(
        override val id: String,
        val image: String,
        val title: String,
        val subtitle: String,
        override val favoriteAction: ApiAction.ApiCommandAddToFavorites,
        override val unFavoriteAction: ApiAction.ApiCommandRemoveFromFavorites,
    ) : ApiFavorite, ApiSectionHeader()

    @Serializable
    data class ApiUnknown(val type: String, override val id: String? = null) : ApiSectionHeader()
}