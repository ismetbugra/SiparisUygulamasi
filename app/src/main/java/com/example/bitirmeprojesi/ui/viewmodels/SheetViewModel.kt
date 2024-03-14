package com.example.bitirmeprojesi.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.Adres
import com.example.bitirmeprojesi.data.entity.SiparisYemekler
import com.example.bitirmeprojesi.data.yemeklerrepository.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SheetViewModel @Inject constructor(var yrepo:YemeklerRepository):ViewModel() {

    var adres=MutableLiveData<Adres>()
    var siparisler=MutableLiveData<List<SiparisYemekler>>()

     fun adresEkle(adres: Adres){
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.adresEkle(adres)
        }
    }

    fun adresGetir(adres_adi:String) {
        CoroutineScope(Dispatchers.Main).launch {
            adres.value= yrepo.adresGetir(adres_adi)
        }

    }

    fun siparisEkle(siparis:SiparisYemekler){
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.siparisEkle(siparis)
        }
    }

    fun siparisleriGetir(kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            siparisler.value=yrepo.siparisleriGetir(kullanici_adi)
        }
    }
}