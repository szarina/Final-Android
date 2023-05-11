package com.example.finalandroid

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.User
import com.example.finalandroid.databinding.FragmentFavoriteBinding
import com.example.finalandroid.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater)
        val username = arguments?.getString("username")
        val id = arguments?.getInt("user_id", -1)
        val email = arguments?.getString("email")

        binding.nameProfile.text = username
        binding.emailProfile.text=email

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() =Profile()

    }
}