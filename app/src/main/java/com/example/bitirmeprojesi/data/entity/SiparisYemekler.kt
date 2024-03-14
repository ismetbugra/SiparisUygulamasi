package com.example.bitirmeprojesi.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "siparis")
data class SiparisYemekler(@PrimaryKey(autoGenerate = true)
                           @NotNull var siparis_id:Int,
                           @NotNull var siparis_yemekler:String,
                           @NotNull var siparis_toplam_fiyat:String,
                           @NotNull var kullanici_adi:String,
                           @NotNull var adres_id:Int) {
}