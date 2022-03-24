package com.gerija.giphy.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerija.giphy.data.remote.api.dto.Data
import com.gerija.giphy.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class GifsViewModel @Inject constructor(
    private val getTopGifsUseCase: GetTopGifsUseCase,
    private val getSearchGifsUseCase: GetSearchGifsUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val insertDatabaseUseCase: InsertDatabaseUseCase
) : ViewModel() {

    private var topGifs = MutableLiveData<List<Data>>()
    val _topGifs: LiveData<List<Data>> get() = topGifs

    private var searchGigs = MutableLiveData<List<Data>>()
    val _searchGifs: LiveData<List<Data>> get() = searchGigs


    var firstVisitSingleAct = true
    var gifsListSAct = ArrayList<Data>()
    var gifsPosSAct = 0

    var firstVisitMyAct = true
    var gifsListMyAct = ArrayList<Data>()

    var offsetSearchMyAct = 0
    var offsetTopMyAct = 0


    /**
     * Загружаю данные в базу
     * В бесплатной версии Api лимит 50шт за раз, делаю через цикл, максимум дает 3950шт
     */
    fun loadNet() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var count = 0
                while (true) {
                    insertDatabaseUseCase(loadDataUseCase(count))
                    count += 50
                    if (count == 3950) break
                }
            } catch (e: Exception) {
                Log.d("MyLog", "$e")
            }
        }
    }

    /**
     * Получаю рейтинговые gif
     */
    fun getTopGifs(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            topGifs.postValue(getTopGifsUseCase(offset))
        }
    }

    /**
     * Получаю gif по запросу поиска
     */
    fun getSearchGifs(searchQuery: String, offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            searchGigs.postValue(getSearchGifsUseCase("%$searchQuery%", offset))
        }
    }

    /**
     * Удаляю с базы gif
     */
    fun deleteItem(data: Data) {
        deleteUseCase(data)
    }
}
