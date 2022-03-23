package com.gerija.giphy.domain


import javax.inject.Inject

class GetTopGifsUseCase @Inject constructor(private val repository: LocalRepository) {

    suspend operator fun invoke(offset: Int) = repository.getTopGifs(offset)
}