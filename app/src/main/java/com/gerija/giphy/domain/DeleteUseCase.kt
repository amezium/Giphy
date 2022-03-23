package com.gerija.giphy.domain

import com.gerija.giphy.data.remote.api.dto.Data
import javax.inject.Inject

class DeleteUseCase @Inject constructor(private val repository: LocalRepository) {
    operator fun invoke(data: Data) = repository.delete(data)
}