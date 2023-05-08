package com.example.finalandroid.api

import com.example.finalandroid.data_classes.Comment
import com.example.finalandroid.data_classes.Favorite
import com.example.finalandroid.data_classes.Film
import com.example.finalandroid.data_classes.User
import retrofit2.Call
import retrofit2.http.*

interface API_service {
    @GET("films")
    fun getFilms(): Call<ArrayList<Film>>

    @GET("films/{film_id}")
    fun getFilm(@Path("film_id") film_id: Int): Call<Film>


    @GET("films/{film_id}/comments")
    fun  getFilmComments(@Path("film_id") film_id: Int):Call <ArrayList<Comment> >


    @POST("films/{film_id_path}/comments")
    fun  addComment(@Path("film_id_path") film_id_path: Int,
                    @Field("user") user_id: Int,
                    @Field("film") film_id: Int ,
                    @Field("content") content:String):Call <Comment>


    @GET("users")
    fun getUsers(): Call <ArrayList <User> >

    @POST("users")
    fun addUser(@Field("usernaame") username: String,
                @Field("email") email: String ,
                @Field("password") password:String) :Call <User>

    @GET("users/{user_id}/favorites")
    fun getUserFavorites(@Path("user_id") user_id: Int ): Call< ArrayList <Favorite> >



    @POST("users/{user_id_path}/favorites")
    fun addUserFavorites(@Path("user_id_path") user_id_path: Int,
                         @Field("user") user_id: Int,
                         @Field("film") film_id: Int ): Call< Favorite>






}