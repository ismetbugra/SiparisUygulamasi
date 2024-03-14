package com.example.bitirmeprojesi.data.yemeklerrepository

import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.data.entity.Adres
import com.example.bitirmeprojesi.data.entity.FavoriYemek
import com.example.bitirmeprojesi.data.entity.SiparisYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.datasource.YemeklerDataSource

class YemeklerRepository(var yds:YemeklerDataSource) {

    suspend fun tumYemekleriGetir():List<Yemekler> = yds.tumYemekleriGetir()

    suspend fun sepeteYemekEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String)
    = yds .sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)

    suspend fun sepettekiyemekleriGetir(kullanici_adi:String) = yds .sepettekiYemekleriGetir(kullanici_adi)

    suspend fun sepettekiYemekSil(sepet_yemek_id:Int,kullanici_adi:String) = yds.sepettekiYemekSil(sepet_yemek_id,kullanici_adi)

    fun favoriKaydet(yemek_id:Int,yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:String) = yds.favoriKaydet(yemek_id,yemek_adi,yemek_resim_adi,yemek_fiyat)

    fun favoriSil(doc_id:String) = yds.sil(doc_id)

    fun favoriYukle(): MutableLiveData<List<FavoriYemek>> = yds.favoriYukle()

    suspend fun adresEkle(adres: Adres) = yds.adresEkle(adres)

    suspend fun adresGetir(adres_adi:String):Adres = yds.adresGetir(adres_adi)

    suspend fun siparisEkle(siparis: SiparisYemekler) = yds.siparisEkle(siparis)

    suspend fun siparisleriGetir(kullanici_adi: String):List<SiparisYemekler> = yds.siparisleriGetir(kullanici_adi)
}