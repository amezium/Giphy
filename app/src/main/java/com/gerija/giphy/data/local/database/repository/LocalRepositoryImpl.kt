package com.gerija.giphy.data.local.database.repository


import com.gerija.giphy.data.remote.api.dto.Data
import com.gerija.giphy.data.local.database.GifsDao
import com.gerija.giphy.domain.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val dao: GifsDao) : LocalRepository {

    /**
     * Получаю рейтинговые gif
     */
    override suspend fun getTopGifs(offset: Int): List<Data> {
        return dao.readData(offset)
    }

    /**
     * Получаю gif по запросу поиска
     */
    override suspend fun getSearchGifs(searchQuery: String, offset: Int): List<Data> {
        return dao.searchDataBase(searchQuery, offset)
    }

    /**
     * Удаляю с базы gif
     */
    override fun delete(data: Data) {
        dao.delete(data)
    }

    /**
     * Наполняю базу данными с Api
     */
    override fun insertDatabase(list: List<Data>) {
        dao.insertData(list)
    }
}