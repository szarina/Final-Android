package com.example.finalandroid.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.finalandroid.R
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.User
import com.example.finalandroid.databinding.ActivityLoginPageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Login_page : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.singInBtn.setOnClickListener {
            val username = binding.singInEmail.text.toString().trim()
            val password = binding.singInPassword.text.toString().trim()

            // Perform API call for authentication
            authenticateUser(username, password)
        }
    }

    private fun authenticateUser(username: String, password: String) {
        // Make API call to authenticate user
        val apiService = API_instance.getApiInstance().create(API_service::class.java)
        val call = apiService.getUsers()

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                val authResponse = response.body()

                if (response.isSuccessful == true) {
                    // Authentication successful, navigate to welcome page
                    val intent = Intent(this@Login_page, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Authentication failed, show error message
                    Toast.makeText(this@Login_page, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                // API call failed, show error message
                Toast.makeText(this@Login_page, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

