package com.example.finalandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalandroid.Login
import com.example.finalandroid.R
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.User
import com.example.finalandroid.databinding.FragmentSignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class sign_up : Fragment() {
    lateinit var  binding: FragmentSignUpBinding
    val emailPattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+").toRegex()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        binding.progressBarClientReg.visibility = View.INVISIBLE

        binding.SingInRegClient.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.client_frag, Login.newInstance())?.commit()
            (activity as AppCompatActivity).supportActionBar?.title = "User Sign In"
        }

        binding.clientRegBtn.setOnClickListener {
            var username = binding.clientusernameIn
            var email = binding.clientemailIn
            var password = binding.clientpassRegIn
            var conf_pass = binding.clientpassRegConfIn


            if(username.text.toString().isEmpty()) username.error = "Empty username"
            else if(!email.text.toString().matches(emailPattern)) email.error = "Invalid email!"
            else if(password.text.toString().length <5) password.error = "Password must be more than 6"
            else if(conf_pass.text.toString() != password.text.toString()) conf_pass.error = "Don't same passwords"
            else{
                val temp = User(
                    null,
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )

                val retrofit = API_instance.getApiInstance()
                val service = retrofit.create(API_service::class.java)
                val call = service.getUsers()
                binding.progressBarClientReg.visibility = View.VISIBLE

                call.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        var users = response.body()

                        Log.d("users", "${users?.size}")
                        var check = true

                        users?.forEach{
                            if(it.email == email.text.toString()){
                                email.error = "This email already registered!"
                                check = false
                            }
                        }

                        Log.d("check", "$check")
                        if(check){
                            CoroutineScope(Dispatchers.IO).launch {
                                service.addUser(temp.username, temp.email, temp.password)
                                if(response.isSuccessful){
                                    Thread(Runnable {
                                        activity?.runOnUiThread(java.lang.Runnable {
                                            Toast.makeText(activity, "You are successfully registered", Toast.LENGTH_SHORT).show()
                                        })
                                    }).start()                              }
                                else{
                                    Thread(Runnable {
                                        activity?.runOnUiThread(java.lang.Runnable {
                                            Toast.makeText(activity, "something wrong", Toast.LENGTH_SHORT).show()
                                        })
                                    }).start()
                                }
                            }
                        }
                        binding.progressBarClientReg.visibility = View.INVISIBLE
                    }
                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
                        binding.progressBarClientReg.visibility = View.INVISIBLE
                    }
                })
            }

        }

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = sign_up()
    }
}