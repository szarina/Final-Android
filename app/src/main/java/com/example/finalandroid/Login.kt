package com.example.finalandroid
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalandroid.R
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.User
import com.example.finalandroid.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Login : Fragment() {
    lateinit var binding: FragmentLoginBinding
    val emailPattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+").toRegex()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.progressBarClientSingin.visibility = View.INVISIBLE

        binding.registerClient.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.client_frag, Login.newInstance())?.commit()
            (activity as AppCompatActivity).supportActionBar?.title = "Client Registration"
        }

        binding.clientEnter.setOnClickListener {
            var email = binding.clientloginIn
            var password = binding.clientPasswordIn

            if(!email.text.toString().matches(emailPattern)) email.error = "Invalid email!"
            else if(password.text.toString().isEmpty()) password.error = "Emoty password!"
            else{
                val api = API_instance.getApiInstance().create(API_service::class.java)
                val call = api.getUsers()
                binding.progressBarClientSingin.visibility = View.VISIBLE
                call.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        var users = response.body()

                        Log.d("users", "${users?.size}")
                        Log.d("is_succesfull", "${response?.isSuccessful}")

                        Toast.makeText(activity, "responce work", Toast.LENGTH_SHORT).show()
                        binding.progressBarClientSingin.visibility = View.INVISIBLE
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
                        Log.d("users", "${t.message}")
                        binding.progressBarClientSingin.visibility = View.INVISIBLE

                    }
                })
            }
        }

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = Login()
    }
}