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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject

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
        etConf_pass_up = findViewById(R.id.conf_pass_up)
        btnRegister = findViewById(R.id.sing_up_bnt)

        btnRegister.setOnClickListener {
            if (etUsername.text.toString().isEmpty()) etUsername.error = "Empty username"
            else if (etPassword.text.toString().length < 5) etPassword.error =
                "Password must be more than 6"
            else if (etConf_pass_up.text.toString() != etPassword.text.toString()) etConf_pass_up.error =
                "Don't same passwords"
            else {

                registerUser(
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
    }
    fun registerUser(username: String, email: String, password: String) {

        val apiService = API_instance.getApiInstance().create(API_service::class.java)
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("password",password)
        jsonObject.put("email", email)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val call = apiService.addUser2(requestBody)
        if(call.isSuccessful){
            navigateToLogin()
        }
        else{
            Toast.makeText(this@Sign_up, "Network error occurred", Toast.LENGTH_SHORT).show()
        }




    }

    private fun navigateToLogin() {
        val intent = Intent(this, Login_page::class.java)
        startActivity(intent)
        finish()
    }
}