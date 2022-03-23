package com.gerija.giphy.domain

import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: RemoteRepository) {
    suspend operator fun invoke() = repository.loadData()
}