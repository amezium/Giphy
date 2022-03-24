package com.gerija.giphy.domain

import com.gerija.giphy.data.remote.api.dto.Data
import javax.inject.Inject

class InsertDatabaseUseCase @Inject constructor(private val repository: LocalRepository) {
    operator fun invoke(list: List<Data>) = repository.insertDatabase(list)
}