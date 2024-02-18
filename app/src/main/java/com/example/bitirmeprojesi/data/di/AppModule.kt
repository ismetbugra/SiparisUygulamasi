package com.example.bitirmeprojesi.data.di

import com.example.bitirmeprojesi.data.retrofit.ApiUtils
import com.example.bitirmeprojesi.data.retrofit.YemeklerDao
import com.example.bitirmeprojesi.data.yemeklerrepository.YemeklerRepository
import com.example.bitirmeprojesi.datasource.YemeklerDataSource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideYemeklerRepository(yds:YemeklerDataSource):YemeklerRepository{
        return YemeklerRepository(yds)
    }

    @Provides
    @Singleton
    fun provideYemeklerDataSource(ydao:YemeklerDao,collectionFavoriler:CollectionReference):YemeklerDataSource{
        return YemeklerDataSource(ydao,collectionFavoriler)
    }

    @Provides
    @Singleton
    fun provideYemeklerDao():YemeklerDao{
        return ApiUtils.getYemeklerDao()
    }

    @Provides
    @Singleton
    fun provideCollection():CollectionReference{
        return Firebase.firestore.collection("Favoriler")
    }


}