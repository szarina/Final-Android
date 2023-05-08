package com.example.finalandroid.api

import com.example.finalandroid.data_classes.Comment
import com.example.finalandroid.data_classes.Film
import com.example.finalandroid.data_classes.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API_service {
    @GET("films")
    fun getFilms(): Call<ArrayList<Film>>

    @GET("films/{film_id}")
    fun getFilm(@Path("film_id") film_id: Int): Call<Film>


    @GET("films/{film_id}/comments")
    fun  getFilmComments(@Path("film_id") film_id: Int):Call <ArrayList<Comment> >


    @POST("films/{film_id}/comments")
    fun  addComment(@Path("film_id") film_id: Int,@Body comment: Comment):Call <Comment>


    @GET("users")
    fun getUsers(): Call <ArrayList <User> >


    @GET("users/{user_id}/favorites")
    fun getUserFavorites(@Path("user_id") user_id: Int )

    





}