package com.example.bitirmeprojesi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bitirmeprojesi.data.entity.Adres
import com.example.bitirmeprojesi.data.entity.SiparisYemekler

@Database(entities = [SiparisYemekler::class,Adres::class], version = 1)
abstract class DatabaseSiparis:RoomDatabase() {
    abstract fun getSiparisDao():SiparisDao
    abstract fun getAdresDao():AdresDao
}