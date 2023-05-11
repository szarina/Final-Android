package com.example.finalandroid

<<<<<<< HEAD
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
=======

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
>>>>>>> 782e1f0f3203e66bdcf30eaee6a38056b6997792
import com.example.finalandroid.activities.Login_page
import com.example.finalandroid.activities.Sign_up
import com.example.finalandroid.databinding.ActivityFirstWelcomeBinding

class FirstWelcome : AppCompatActivity() {

    lateinit var binding: ActivityFirstWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        binding= ActivityFirstWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.singin.setOnClickListener{
            val intent= Intent(this, Login_page::class.java)
            startActivity(intent)
        }

        binding.singup.setOnClickListener{
            val intent= Intent(this, Sign_up::class.java)
            startActivity(intent)
        }




=======
        binding = ActivityFirstWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
>>>>>>> 782e1f0f3203e66bdcf30eaee6a38056b6997792

        binding.singin.setOnClickListener {
            val intent = Intent(this, Login_page::class.java)
            startActivity(intent)
        }

        binding.singup.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }

    }
}