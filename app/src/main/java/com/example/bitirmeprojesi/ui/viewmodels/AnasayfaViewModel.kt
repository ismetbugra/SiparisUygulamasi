package com.example.bitirmeprojesi.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.data.yemeklerrepository.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(var yrepo:YemeklerRepository):ViewModel() {

    val yemeklerList=MutableLiveData<List<Yemekler>>()

    init {
        tumYemekleriGetir()
    }

    fun tumYemekleriGetir(){
        CoroutineScope(Dispatchers.Main).launch {
            yemeklerList.value=yrepo.tumYemekleriGetir()
        }
    }

}