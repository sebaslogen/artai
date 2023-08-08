package com.sebaslogen.artai.data.remote.models

interface ApiFavorite {
    val id: String
    val favoriteAction: ApiAction.ApiCommandAddToFavorites
    val unFavoriteAction: ApiAction.ApiCommandRemoveFromFavorites
}