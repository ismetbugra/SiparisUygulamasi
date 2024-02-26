package com.example.bitirmeprojesi.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.yemeklerrepository.YemeklerRepository
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SepetViewModel @Inject constructor(var yrepo: YemeklerRepository):ViewModel() {
    val sepetYemeklerList=MutableLiveData<List<SepetYemekler>>()
    val auth= Firebase.auth

    init {
        sepettekiYemekleriGetir(auth.currentUser!!.email.toString())
    }

    fun sepettekiYemekleriGetir(kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            sepetYemeklerList.value=yrepo.sepettekiyemekleriGetir(kullanici_adi)
        }
    }

    fun sepettekiYemekSil(sepet_yemek_id:Int,kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.sepettekiYemekSil(sepet_yemek_id,kullanici_adi)
            sepettekiYemekleriGetir(auth.currentUser!!.email.toString())
        }
    }
}