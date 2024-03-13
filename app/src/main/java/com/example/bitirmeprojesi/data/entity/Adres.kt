package com.example.bitirmeprojesi.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "adres")
data class Adres(@PrimaryKey(autoGenerate = true)
                 @NotNull var adres_id:Int,
                 @NotNull var adres_bilgisi:String) {
}