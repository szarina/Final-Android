package com.example.finalandroid.api

import com.example.finalandroid.data_classes.Film
import retrofit2.Call
import retrofit2.http.GET

interface API_service {
    @GET("films") //?country=us&category=business&apiKey=8ad925c3ce3a4309b73812315bbc7e97
    fun getFilms(): Call<ArrayList<Film>>
}