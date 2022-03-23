package com.gerija.giphy.data.remote.api

import com.gerija.giphy.data.remote.api.dto.GifsContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("trending")
    suspend fun getGifsAreTrending(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = RATING
    ): GifsContainer

    companion object{
        private const val API_KEY = "U6BGrK0dzxJPCZy8fi2Bl0LuEeyVG5oW"
        private const val RATING = "g"
    }
}