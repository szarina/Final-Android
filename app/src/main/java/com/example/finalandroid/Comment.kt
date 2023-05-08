package com.example.finalandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalandroid.databinding.ActivityCommentBinding

class Comment : AppCompatActivity() {
    lateinit var binding:ActivityCommentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}