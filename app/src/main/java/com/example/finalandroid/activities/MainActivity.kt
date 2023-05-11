package com.example.finalandroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.finalandroid.favorities.FavoriteFilms
import com.example.finalandroid.movies.Home
import com.example.finalandroid.Profile
import com.example.finalandroid.R
import com.example.finalandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras as Bundle
        val username = bundle.getString("username")
        val email = bundle.getString("email")
        val user_id = bundle.getInt("user_id")



        replaceFragment(Home(),username,email ,user_id)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(Home(),username,email,user_id)
                R.id.add -> replaceFragment(FavoriteFilms(),username, email,user_id)
                R.id.profile -> replaceFragment(Profile(),username ,email,user_id)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment,username :String?,email :String?, id : Int) {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()

        val args = Bundle()
        args.putString("username", username)
        args.putInt("user_id", id)
        args.putString("email", email)
        fragment.arguments = args

        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }



}