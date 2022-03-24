package com.gerija.giphy.domain

import com.gerija.giphy.data.remote.api.dto.Data

interface LocalRepository {

    /**
     * Получаю рейтинговые gif
     */
    suspend fun getTopGifs(offset: Int): List<Data>

    /**
     * Получаю gif по запросу поиска
     */
    suspend fun getSearchGifs(searchQuery: String, offset: Int): List<Data>

    /**
     * Удаляю с базы gif
     */
    fun delete(data: Data)

    /**
     * Наполняю базу данными с Api
     */
    fun insertDatabase(list: List<Data>)
}