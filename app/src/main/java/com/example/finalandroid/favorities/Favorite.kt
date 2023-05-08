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
import com.example.finalandroid.databinding.FragmentHomeBinding


class Favorite : Fragment() {

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
            val db = FavoritesDatabase.getFruitsDb(this@FavoriteFruits)
            db.getFruitsDao().getAllSavedFruits().asLiveData().observe(this@FavoriteFruits){
                if(it.size == 0){
                    Toast.makeText(this@FavoriteFruits, "No saved fruits", Toast.LENGTH_SHORT).show()
                }
                layoutManager = LinearLayoutManager(this@FavoriteFruits)
                recAdapter = FavoritesAdapter(it, this@FavoriteFruits)
                recAdapter.setOnItemClickListener(object : FavoritesAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        intent = Intent(this@FavoriteFruits, ItemDetails::class.java)
                        intent.putExtra("name", it[position].name)
                        intent.putExtra("id", it[position].id)
                        intent.putExtra("family", it[position].family)
                        intent.putExtra("genus", it[position].genus)
                        intent.putExtra("order", it[position].order)
//                            intent.putExtra("image_url", it[position].image_url)

                        intent.putExtra("carbohydrates", it[position].carbohydrates)
                        intent.putExtra("protein", it[position].protein)
                        intent.putExtra("fat", it[position].fat)
                        intent.putExtra("calories", it[position].calories)
                        intent.putExtra("sugar", it[position].sugar)
                        startActivity(intent)
                    }
                })
                adapter = recAdapter
                recAdapter.notifyDataSetChanged()
            }
        }
    }
    override fun onBackPressed() {
        if (backPressedTime + 10 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            builder.setTitle("Exit Saves Fruits")
                .setMessage("Get out from favourite fruits ?")
                .setPositiveButton("Yes"){id, it ->
                    val intent = Intent(this, MainActivity::class.java)
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
        fun newInstance() = Favorite()

    }
}