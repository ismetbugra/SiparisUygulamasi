package com.example.bitirmeprojesi.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bitirmeprojesi.data.entity.SiparisYemekler

@Dao
interface SiparisDao {

    @Insert
    suspend fun insertSiparis(siparis:SiparisYemekler)

    @Query("SELECT*FROM siparis WHERE kullanici_adi = :kullanici_adi")
    suspend fun getAllSiparis(kullanici_adi:String):List<SiparisYemekler>
}