package com.example.coursekt.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private val URL = "https://dragonball-api.com/"

    private val retrofit = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()
    fun getInstance(): APISuperHero {
        return retrofit.create(APISuperHero::class.java)
    }

}