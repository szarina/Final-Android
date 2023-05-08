package com.example.finalandroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalandroid.Login
import com.example.finalandroid.R
import com.example.finalandroid.databinding.ActivityLoginPageBinding

class Login_page: AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.client_frag, Login.newInstance()).commit()
        supportActionBar?.title = "Client Sign In"

    }
}