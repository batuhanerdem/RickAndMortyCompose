package com.example.rickandmortycompose.di

import com.example.rickandmortycompose.data.repository.CharacterRepositoryImpl
import com.example.rickandmortycompose.data.repository.EpisodeRepositoryImpl
import com.example.rickandmortycompose.data.repository.LocationRepositoryImpl
import com.example.rickandmortycompose.data.service.CharacterService
import com.example.rickandmortycompose.data.service.EpisodeService
import com.example.rickandmortycompose.data.service.LocationService
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.domain.repository.EpisodeRepository
import com.example.rickandmortycompose.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideCharacterRepository(service: CharacterService): CharacterRepository =
        CharacterRepositoryImpl(service)

    @Provides
    fun provideLocationRepository(service: LocationService): LocationRepository =
        LocationRepositoryImpl(service)

    @Provides
    fun provideEpisodeRepository(service: EpisodeService): EpisodeRepository =
        EpisodeRepositoryImpl(service)
}