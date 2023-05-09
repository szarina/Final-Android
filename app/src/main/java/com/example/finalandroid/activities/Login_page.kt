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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login_page : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        etUsername = findViewById(R.id.sing_in_email)
        etPassword = findViewById(R.id.sing_in_password)
        btnLogin = findViewById(R.id.sing_in_btn)
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            // Make API request for user login
            loginUser(username, password)
        }
    }
    private fun loginUser(username: String, password: String) {
        val apiService = API_instance.getApiInstance().create(API_service::class.java) // Assuming you have a RetrofitClient class for API service instance

        val call = apiService.getUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val user = response.body()?.user

                    if (user != null) {
                        // Login successful, navigate to welcome page and pass user information
                        navigateToMainActivity(user)
                    } else {
                        // Invalid response or user information missing
                        Toast.makeText(this@Login_page, "Invalid response from server", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Login failed, display error message
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(this@Login_page, errorResponse, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Network error occurred
                Toast.makeText(this@Login_page, "Network error occurred", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun navigateToMainActivity(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

}
