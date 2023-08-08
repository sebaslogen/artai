package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiCacheResponse(
    val cache: ApiCacheData? = null
)

/**
 * Dictionary of cached data that can be updated on any response.
 */
@Serializable
data class ApiCacheData(
    /**
     * List of currently favorited items.
     * Important note: "null" value means there is no update on the list of favorites. Only a non-null empty list means the list is empty.
     */
    val favorites: List<String>? = null // TODO: Use here and in all other ids a value class: @JvmInline value class ArtId(val value: String)
)