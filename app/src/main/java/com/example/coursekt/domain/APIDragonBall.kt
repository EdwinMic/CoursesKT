package com.example.coursekt.domain

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET

interface APISuperHero {

    //@GET("/api/f249036d953c7cab5f8b2a292fe00a87/search/{name}")
    @GET("/api/characters/1")
    //suspend fun getSuperHero(@Path("name") superHeroName: String): Response<SuperHeroDataResponse>
    suspend fun getSuperHero(): Response<PersonDataResponse>
}

data class PersonDataResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("ki") val ki: String,
    @SerializedName("maxKi") val maxKi: String,
    @SerializedName("race") val race: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("affiliation") val affiliation: String,
)