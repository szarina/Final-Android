package com.example.finalandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast


import com.bumptech.glide.Glide


import androidx.core.content.ContentProviderCompat.requireContext
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service

import com.example.finalandroid.comment.Comment
import com.example.finalandroid.data_classes.Favorite
import com.example.finalandroid.data_classes.Rating_res
import com.example.finalandroid.databinding.ActivityFilmDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class filmDetails : AppCompatActivity() {
    lateinit var binding: ActivityFilmDetailsBinding

    lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityFilmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.comMovie.setOnClickListener {
            val intent = Intent(this, Comment::class.java)
            startActivity(intent)
        }
        try{
        bundle = intent.extras!!
        val user_id = bundle.getInt("user_id")
        val film_id = bundle.getInt("film_id")
        val username = bundle.getString("username")
        val description = bundle.getString("description")
        val title = bundle.getString("title")
        val photoLink = bundle.getString("photoLink")
        Log.d("data","user_id - ${user_id} ,film_id - ${film_id}" +
                ",description- ${description},title -${title}" +
                "+photoLink - ${photoLink}")
        createPage(user_id, film_id, description!!, title!!, photoLink!!)}
        catch (exception :java.lang.Exception){
            exception.printStackTrace()
        }

//        binding.ratingBar.setOnClickListener {
//            val msg = binding.ratingBar.rating
//            val film_id=1
////            Toast.makeText(this@filmDetails,
////                "Rating is: "+msg, Toast.LENGTH_SHORT).show()
//            submitRating(film_id, msg)
//        }

        binding.saveImgBtn.setOnClickListener {
//            addFilmToFavorites(user_id,film_id)
            addFilmToFavorites(1,1)
        }
    }

    private fun createPage(
        user_id: Int,
        film_id: Int,
        description: String,
        title: String,
        photoLink: String
    ) {
        binding.titleDet.text = title
        binding.descriptionDet.text = description
        binding.ratingTxt.text = getRatingOfFilm(film_id).toString()
        val img = binding.imageView3
        val url = photoLink

        Glide.with(img)
            .load(url)
            .placeholder(R.drawable.image)
            .error(R.drawable.image)
            .fallback(R.drawable.image)
            .into(img)


    }

    private fun submitRating(filmId: Int, ratingValue: Float) {
        val apiService = API_instance.getApiInstance().create(API_service::class.java)
        val call = apiService.getRatingForFilm(filmId)

        call.enqueue(object : Callback<ArrayList<Rating_res>> {
            override fun onResponse(
                call: Call<ArrayList<Rating_res>>,
                response: Response<ArrayList<Rating_res>>
            ) {
                if (response.isSuccessful) {
                    // Rating submitted successfully
                    val ratingList = response.body()
                    // Handle the API response as needed
                    Toast.makeText(
                        this@filmDetails,
                        "Rating submitted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Rating submission failed
                    val errorMessage = response.errorBody()?.string()
                    // Handle the error message
                    Toast.makeText(
                        this@filmDetails,
                        "Rating submission failed: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<ArrayList<Rating_res>>, t: Throwable) {
                // Handle the failure scenario
                Toast.makeText(
                    this@filmDetails,
                    "Rating submission failed: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun addFilmToFavorites(user_id:Int,film_id:Int) {
        val apiService = API_instance.getApiInstance().create(API_service::class.java)
        val call = apiService.addUserFavorites(user_id, user_id, film_id)

        call.enqueue(object : Callback<ArrayList<Favorite>> {
            override fun onResponse(
                call: Call<ArrayList<Favorite>>,
                response: Response<ArrayList<Favorite>>
            ) {
                if (response.isSuccessful) {
                    // Rating submitted successfully
                    val ratingList = response.body()
                    // Handle the API response as needed
                }
            }


            override fun onFailure(call: Call<ArrayList<Favorite>>, t: Throwable) {
                // Handle the failure scenario
                Toast.makeText(
                    this@filmDetails,
                    "Couldn't find rating of a film: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }



    private fun getRatingOfFilm(film_id: Int, ) :Float{
        val apiService = API_instance.getApiInstance().create(API_service::class.java)
        val call = apiService.getRatingForFilm(film_id)
        var result: Float? = 0.0f
        call.enqueue(object : Callback<ArrayList<Rating_res>> {
            override fun onResponse(
                call: Call<ArrayList<Rating_res>>,
                response: Response<ArrayList<Rating_res>>
            ) {
                if (response.isSuccessful) {
                    // Rating submitted successfully
                    val ratingList = response.body()
                    // Handle the API response as needed
                    result = ratingList?.get(0)?.rating
                }
            }


            override fun onFailure(call: Call<ArrayList<Rating_res>>, t: Throwable) {
                // Handle the failure scenario
                Toast.makeText(
                    this@filmDetails,
                    "Couldn't find rating of a film: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        return result!!;
    }


}