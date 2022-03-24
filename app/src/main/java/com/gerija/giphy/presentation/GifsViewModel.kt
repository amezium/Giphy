package com.gerija.giphy.presentation

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerija.giphy.data.remote.api.dto.Data
import com.gerija.giphy.domain.DeleteUseCase
import com.gerija.giphy.domain.GetSearchGifsUseCase
import com.gerija.giphy.domain.GetTopGifsUseCase
import com.gerija.giphy.domain.LoadDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GifsViewModel @Inject constructor(
    private val getTopGifsUseCase: GetTopGifsUseCase,
    private val getSearchGifsUseCase: GetSearchGifsUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val application: Application
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
     */
    fun loadNet() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadDataUseCase()
            } catch (e:Exception){
                launch(Dispatchers.Main) {
                    Toast.makeText(application, "No Internet", Toast.LENGTH_LONG).show()
                }
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
