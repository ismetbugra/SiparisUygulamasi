package com.example.bitirmeprojesi.data.di

import android.content.Context
import androidx.room.Room
import com.example.bitirmeprojesi.data.retrofit.ApiUtils
import com.example.bitirmeprojesi.data.retrofit.YemeklerDao
import com.example.bitirmeprojesi.data.room.AdresDao
import com.example.bitirmeprojesi.data.room.DatabaseSiparis
import com.example.bitirmeprojesi.data.room.SiparisDao
import com.example.bitirmeprojesi.data.yemeklerrepository.YemeklerRepository
import com.example.bitirmeprojesi.datasource.YemeklerDataSource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideYemeklerDataSource(ydao:YemeklerDao,collectionFavoriler:CollectionReference,sdao: SiparisDao,adao:AdresDao):YemeklerDataSource{
        return YemeklerDataSource(ydao,collectionFavoriler,sdao,adao)
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

    @Provides
    @Singleton
    fun provideSiparisDao(@ApplicationContext context: Context):SiparisDao{
        return Room.databaseBuilder(context.applicationContext,DatabaseSiparis::class.java,"siparisler.sqlite")
            .createFromAsset("siparisler.sqlite").build().getSiparisDao()
    }

    @Provides
    @Singleton
    fun provideAdresDao(@ApplicationContext context: Context):AdresDao{
        return Room.databaseBuilder(context.applicationContext,DatabaseSiparis::class.java,"siparisler.sqlite")
            .createFromAsset("siparisler.sqlite").build().getAdresDao()
    }


}