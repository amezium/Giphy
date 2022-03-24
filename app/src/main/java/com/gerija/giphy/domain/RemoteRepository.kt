package com.gerija.giphy.domain

import com.gerija.giphy.data.remote.api.dto.Data

interface RemoteRepository {

    /**
     * Загружаю данные с сети в базу
     */
    suspend fun loadData(offset: Int): List<Data>
}