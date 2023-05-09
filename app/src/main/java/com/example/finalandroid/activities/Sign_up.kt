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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response

class Sign_up : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var  etConf_pass_up: EditText
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        etUsername = findViewById(R.id.name_up)
        etEmail = findViewById(R.id.emial_up)
        etPassword = findViewById(R.id.passw_up)
        etConf_pass_up= findViewById(R.id.conf_pass_up)
        btnRegister = findViewById(R.id.sing_up_bnt)

        btnRegister.setOnClickListener {
            if(etUsername.text.toString().isEmpty()) etUsername.error = "Empty username"

            else if( etPassword.text.toString().length <5)  etPassword.error = "Password must be more than 6"
            else if(  etConf_pass_up.text.toString() != etPassword.text.toString())   etConf_pass_up.error = "Don't same passwords"
            else{

                val temp = User(
                    null,
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            // Make API request for user registration
//            registerUser(etUsername, etEmail, etPassword)
        }
    }
//    private fun registerUser(username: String, email: String, password: String) {
        val retrofit = API_instance.getApiInstance()
        val service = retrofit.create(API_service::class.java)
        val call = service.getUsers()

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(call:Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                var users = response.body()
                if (response.isSuccessful) {
                    // Registration successful, navigate to login page
                    navigateToLogin()
                } else {
                    // Registration failed, display error message
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(this@Sign_up, errorResponse, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                // Network error occurred
                Toast.makeText(this@Sign_up, "Network error occurred", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToLogin() {
        val intent = Intent(this, Login_page::class.java)
        startActivity(intent)
        finish()
    }
}