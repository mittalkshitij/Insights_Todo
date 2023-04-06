package com.example.todokshitij.di

import com.example.todokshitij.data.api.ApiHelper
import com.example.todokshitij.data.api.ApiHelperImpl
import com.example.todokshitij.data.api.ApiInterface
import com.example.todokshitij.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("url")
    fun provideBaseUrl() : String= Constants.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("url")baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}