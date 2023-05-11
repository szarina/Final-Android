package com.example.finalandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.bumptech.glide.Glide

import com.example.finalandroid.comment.Comment
import com.example.finalandroid.databinding.ActivityFilmDetailsBinding

class filmDetails : AppCompatActivity() {
    lateinit var binding: ActivityFilmDetailsBinding
    lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityFilmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.comMovie.setOnClickListener{
            val intent = Intent(this, Comment::class.java)
            startActivity(intent)
        }


        binding.ratingBar.setOnClickListener {
            val msg = binding.ratingBar.rating.toString()
            Toast.makeText(this@filmDetails,
                "Rating is: "+msg, Toast.LENGTH_SHORT).show()
        }

        binding.saveImgBtn.setOnClickListener{

        }
    }

    private fun createPage(){
        binding.titleDet.text=bundle!!.getString("title")
        binding.descriptionDet.text=bundle!!.getString("description")

        val img = binding.imageView3
        val url=bundle!!.getString("photoLink")

        Glide.with(img)
            .load(url)
            .placeholder(R.drawable.image)
            .error(R.drawable.image)
            .fallback(R.drawable.image)
            .into(img)

    }

}