package com.gerija.giphy.data.remote.api.reposiroty

import com.gerija.giphy.data.remote.api.ApiService
import com.gerija.giphy.data.local.database.GifsDao
import com.gerija.giphy.domain.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val api: ApiService, private val dao: GifsDao)
    : RemoteRepository {

    /**
     * Загружаю данные с Api в базу
     * В бесплатной версии Api лимит 50шт за раз, делаю через цикл, максимум дает 3950шт
     */
    override suspend fun loadData() {
        var count = 0
        while (true){
            dao.insertData(api.getGifsAreTrending(offset = count).data)
            count += 50
            if (count == 3950) break
        }
    }
}