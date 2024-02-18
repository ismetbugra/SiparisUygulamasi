package com.example.bitirmeprojesi.data.retrofit

import retrofit2.create

class ApiUtils {
    //baseurl--> http://kasimadalan.pe.hu/
    companion object{
        val BASE_URL="http://kasimadalan.pe.hu/"

        fun getYemeklerDao():YemeklerDao{
            return RetrofitClient.getClient(BASE_URL).create()
        }
    }
}