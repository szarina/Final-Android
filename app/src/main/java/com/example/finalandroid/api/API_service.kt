package com.example.finalandroid.api

import com.example.finalandroid.data_classes.Film
import com.example.finalandroid.data_classes.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface API_service {
    @GET("films")
    fun getFilms(): Call<ArrayList<Film>>

    @GET("films/{film_id}")
    fun getFilm(@Path("film_id") film_id: Int): Call<Film>

    @GET("users")
    fun getUsers(): Call <ArrayList <User> >




}