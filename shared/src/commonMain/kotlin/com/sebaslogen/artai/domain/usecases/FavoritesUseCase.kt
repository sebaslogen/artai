package com.sebaslogen.artai.domain.usecases

import com.sebaslogen.artai.data.remote.repositories.FavoritesRepository
import me.tatarka.inject.annotations.Inject

@Inject
class FavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {

}