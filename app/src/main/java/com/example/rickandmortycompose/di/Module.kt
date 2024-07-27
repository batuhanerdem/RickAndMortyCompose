package com.example.rickandmortycompose.di

import com.example.rickandmortycompose.data.repository.CharacterRepositoryImpl
import com.example.rickandmortycompose.data.service.CharacterService
import com.example.rickandmortycompose.data.service.ServiceConstants
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.nerdythings.okhttpprofiler.BuildConfig
import io.nerdythings.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(OkHttpProfilerInterceptor())
        }
        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(ServiceConstants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())
        ).client(okHttpClient).build()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    fun provideCharacterRepository(service: CharacterService): CharacterRepository =
        CharacterRepositoryImpl(service)

}