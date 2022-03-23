package com.gerija.giphy.di

import com.gerija.giphy.data.remote.api.reposiroty.RemoteRepositoryImpl
import com.gerija.giphy.data.local.database.repository.LocalRepositoryImpl
import com.gerija.giphy.domain.LocalRepository
import com.gerija.giphy.domain.RemoteRepository
import dagger.Binds
import dagger.Module

@Module
interface GifsModule {

    @ApplicationScope
    @Binds
    fun bindsRepository(impl: LocalRepositoryImpl): LocalRepository

    @ApplicationScope
    @Binds
    fun bindsRemoteRepository(impl: RemoteRepositoryImpl): RemoteRepository
}