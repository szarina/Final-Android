package com.example.finalandroid.favorities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalandroid.databinding.FragmentFavoriteBinding


class FavoriteFilms : Fragment() {

    lateinit var binding:FragmentFavoriteBinding
    lateinit var recAdapter: FavoritesAdapter
    var backPressedTime: Long = 0
    lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavoriteBinding.inflate(inflater)
        builder = AlertDialog.Builder(this)
        setContentView(binding.root)
        supportActionBar?.title = "Saved Fruits"

        initRecyclerView()
    }
    private fun initRecyclerView() {
        var recyclerView  = binding.recyclerSave

        recyclerView.apply {
        }

        }

    override fun onBackPressed() {
        if (backPressedTime + 10 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            builder.setTitle("Exit Saves Fruits")
                .setMessage("Get out from favourite fruits ?")
                .setPositiveButton("Yes"){id, it ->
                    val intent = Intent(this, FavoriteFilms::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("No"){id, it ->
                    id.cancel()
                }
                .show()
        }
        backPressedTime = System.currentTimeMillis()
    }



    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFilms()

    }
}