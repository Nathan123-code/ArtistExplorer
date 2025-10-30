package com.nathan.artistexplorer.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ArtistService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.theaudiodb.com/api/v1/json/123/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtistService::class.java)
    }
}
