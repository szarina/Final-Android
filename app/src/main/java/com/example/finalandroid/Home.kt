package com.example.finalandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalandroid.databinding.FragmentFavoriteBinding
import com.example.finalandroid.databinding.FragmentHomeBinding

class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =Home()

    }
}