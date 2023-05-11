package com.example.finalandroid.api

import com.example.finalandroid.comment.CommentEntity
import com.example.finalandroid.data_classes.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface API_service {
    @GET("films")
    fun getFilms(): Call<ArrayList<Film>>

    @GET("films/{film_id}")
    fun getFilm(@Path("film_id") film_id: Int): Call<Film>


    @GET("films/{film_id}/comments")
    fun  getFilmComments(@Path("film_id") film_id: Int):Call <ArrayList<CommentEntity> >


    @POST("films/{film_id_path}/comments")
    fun  addComment(@Path("film_id_path") film_id_path: Int,
                    @Field("user") user_id: Int,
                    @Field("film") film_id: Int ,
                    @Field("content") content:String):Call <ArrayList<CommentEntity>>


    @GET("users")
    fun getUsers(): Call <ArrayList <User> >

    @FormUrlEncoded
    @POST("users")
    fun addUser(@Field("username") username: String,
                @Field("email") email: String ,
                @Field("password") password:String) :Call < ArrayList<User> >



    @GET("users/{user_id}/favorites")
    fun getUserFavorites(@Path("user_id") user_id: Int ): Call< ArrayList <Favorite> >



    @POST("users/{user_id_path}/favorites")
    fun addUserFavorites(@Path("user_id_path") user_id_path: Int,
                         @Field("user") user_id: Int,
                         @Field("film") film_id: Int ): Call< ArrayList<Favorite>>


    @GET("fims/{film_id}/ratings")
    fun getRatingForFilm(@Path("film_id") film_id: Int): Call <ArrayList <Rating_res>>


    @POST("ratings")
    fun addRating( @Field("user") user_id: Int,
                   @Field("film") film_id: Int,
                   @Field("rating") rating: Float) : Call<ArrayList<Rating>>




}