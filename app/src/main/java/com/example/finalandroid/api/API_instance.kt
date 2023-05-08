package com.example.finalandroid.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API_instance {
    companion object {

        val url = "http://127.0.0.1:8000/api/"
        fun getApiInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}