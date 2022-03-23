package com.gerija.giphy.domain

interface RemoteRepository {

    /**
     * Загружаю данные с сети в базу
     */
    suspend fun loadData()
}