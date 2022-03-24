package com.gerija.giphy.di

import android.app.Application
import com.gerija.giphy.data.remote.api.ApiFactory
import com.gerija.giphy.data.remote.api.ApiService
import com.gerija.giphy.data.local.database.GifsDao
import com.gerija.giphy.data.local.database.GifsDatabase
import com.gerija.giphy.presentation.MainActivity
import dagger.Module
import dagger.Provides

@Module
class GifsModuleProvide(private val application: Application) {

    @Provides
    fun providesApiService(): ApiService {
        return ApiFactory.create()
    }

    @Provides
    fun providesContext(): Application {
        return application
    }

    @Provides
    fun providesDao(): GifsDao {
        return GifsDatabase.getInstance(application).gifsDao()
    }

}