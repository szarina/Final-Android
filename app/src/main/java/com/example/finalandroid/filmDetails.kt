package com.example.finalandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.comment.Comment
import com.example.finalandroid.data_classes.Rating_res
import com.example.finalandroid.databinding.ActivityFilmDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class filmDetails : AppCompatActivity() {
    lateinit var binding: ActivityFilmDetailsBinding
    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityFilmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.comMovie.setOnClickListener{
            val intent = Intent(this, Comment::class.java)
            startActivity(intent)
        }

        binding.ratingBar.setOnClickListener {
            val filmId = "your_film_id"
            val msg = binding.ratingBar.rating
//            Toast.makeText(this@filmDetails,
//                "Rating is: "+msg, Toast.LENGTH_SHORT).show()
            submitRating(filmId,msg)
        }


    }
    private fun submitRating(filmId: String, ratingValue: Float) {
        val apiService = API_instance.getApiInstance().create(API_service::class.java)
        val call = apiService.getRatingForFilm(filmId)

        call.enqueue(object : Callback<ArrayList<Rating_res>> {
            override fun onResponse(call: Call<ArrayList<Rating_res>>, response: Response<ArrayList<Rating_res>>) {
                if (response.isSuccessful) {
                    // Rating submitted successfully
                    val ratingList = response.body()
                    // Handle the API response as needed
                    Toast.makeText(requireContext(), "Rating submitted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Rating submission failed
                    val errorMessage = response.errorBody()?.string()
                    // Handle the error message
                    Toast.makeText(requireContext(), "Rating submission failed: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<Rating_res>>, t: Throwable) {
                // Handle the failure scenario
                Toast.makeText(requireContext(), "Rating submission failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}