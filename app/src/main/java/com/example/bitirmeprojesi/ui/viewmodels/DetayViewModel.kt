package com.example.bitirmeprojesi.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.yemeklerrepository.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetayViewModel @Inject constructor(var yrepo: YemeklerRepository):ViewModel() {

    fun sepeteYemekEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        }
    }

    fun favoriKaydet(yemek_id:Int,yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:String){
        yrepo.favoriKaydet(yemek_id,yemek_adi,yemek_resim_adi,yemek_fiyat)
    }

    fun favoriSil(doc_id:String){
        yrepo.favoriSil(doc_id)
    }
}