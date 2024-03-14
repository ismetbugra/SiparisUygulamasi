package com.example.bitirmeprojesi.datasource

import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.data.entity.Adres
import com.example.bitirmeprojesi.data.entity.FavoriYemek
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.SepetYemeklerCevap
import com.example.bitirmeprojesi.data.entity.SiparisYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.data.retrofit.YemeklerDao
import com.example.bitirmeprojesi.data.room.AdresDao
import com.example.bitirmeprojesi.data.room.SiparisDao
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerDataSource(var ydao:YemeklerDao,var collectionFavoriler:CollectionReference,var sdao:SiparisDao,var adao:AdresDao) {
    var favorilerListesi=MutableLiveData<List<FavoriYemek>>()

    suspend fun tumYemekleriGetir():List<Yemekler>
    = withContext(Dispatchers.IO){
        val yemeklerList=ydao.tumYemekleriGetir().yemekler
        return@withContext yemeklerList
    }

    suspend fun sepeteYemekEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String){
        val cevap = ydao.sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        println(cevap)
    }

    suspend fun sepettekiYemekleriGetir(kullanici_adi:String):List<SepetYemekler> =
        withContext(Dispatchers.IO){
            val cevap=ydao.sepettekiYemekleriGetir(kullanici_adi)
            println(cevap)
            return@withContext cevap.sepet_yemekler
        }

    suspend fun sepettekiYemekSil(sepet_yemek_id:Int,kullanici_adi:String){
        val cevap=ydao.sepettekiYemekSil(sepet_yemek_id,kullanici_adi)
    }

    fun favoriKaydet(yemek_id:Int,yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:String){
        val favori=FavoriYemek(yemek_id,yemek_adi,yemek_resim_adi,yemek_fiyat,"")
        collectionFavoriler.document().set(favori)

    }

    fun favoriYukle():MutableLiveData<List<FavoriYemek>>{
        collectionFavoriler.addSnapshotListener { value, error ->
            if (value!=null){
                val liste=ArrayList<FavoriYemek>()

                for(d in value.documents){
                    val favoriYemek=d.toObject(FavoriYemek::class.java)
                    if(favoriYemek!=null){
                       favoriYemek.doc_id=d.id
                       liste.add(favoriYemek)
                    }
                }
                favorilerListesi.value=liste
            }

        }
        return favorilerListesi
    }

    fun sil(doc_id: String){
        collectionFavoriler.document(doc_id).delete()
    }

    suspend fun adresEkle(adres: Adres){
        adao.insertAdres(adres)
    }

    suspend fun adresGetir(adres_adi:String):Adres{
        return adao.adresGetir(adres_adi)

    }

    suspend fun siparisEkle(siparis:SiparisYemekler){
        sdao.insertSiparis(siparis)
    }

    suspend fun siparisleriGetir(kullanici_adi: String):List<SiparisYemekler> =
        withContext(Dispatchers.IO){
            return@withContext sdao.getAllSiparis(kullanici_adi)
        }
}