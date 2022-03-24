package com.gerija.giphy.data.remote.api.reposiroty

import com.gerija.giphy.data.local.database.GifsDao
import com.gerija.giphy.data.remote.api.ApiService
import com.gerija.giphy.data.remote.api.dto.Data
import com.gerija.giphy.domain.RemoteRepository
import javax.inject.Inject


class RemoteRepositoryImpl @Inject constructor(
    private val api: ApiService
) : RemoteRepository {

    /**
     * Загружаю данные с Api в базу
     */
    override suspend fun loadData(offset: Int): List<Data> {
        return api.getGifsAreTrending(offset = offset).data
    }
}