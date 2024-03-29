package com.example.bitirmeprojesi.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bitirmeprojesi.data.entity.Adres

@Dao
interface AdresDao {

    @Insert
    suspend fun insertAdres(adres: Adres)

    @Query("SELECT*FROM adres")
    suspend fun getALlAdres():List<Adres>

    @Query("SELECT*FROM adres WHERE adres_bilgisi= :adres_adi")
    suspend fun adresGetir(adres_adi:String):Adres
}