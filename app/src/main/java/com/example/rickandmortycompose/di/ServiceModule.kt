package com.example.rickandmortycompose.di

import com.example.rickandmortycompose.data.service.CharacterService
import com.example.rickandmortycompose.data.service.EpisodeService
import com.example.rickandmortycompose.data.service.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ServiceModule {
    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    fun provideLocationService(retrofit: Retrofit): LocationService =
        retrofit.create(LocationService::class.java)

    fun provideEpisodeService(retrofit: Retrofit): EpisodeService =
        retrofit.create(EpisodeService::class.java)
}