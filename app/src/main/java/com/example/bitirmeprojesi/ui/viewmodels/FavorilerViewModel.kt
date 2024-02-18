package com.example.bitirmeprojesi.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.FavoriYemek
import com.example.bitirmeprojesi.data.yemeklerrepository.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavorilerViewModel @Inject constructor(var yrepo: YemeklerRepository): ViewModel() {
    var favorilerListesi= MutableLiveData<List<FavoriYemek>>()

    init {
        favoriYukle()
    }

    fun favoriYukle(){
        favorilerListesi=yrepo.favoriYukle()
    }
}