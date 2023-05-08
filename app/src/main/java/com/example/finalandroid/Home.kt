package com.example.finalandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.Film
import com.example.finalandroid.databinding.FragmentFavoriteBinding
import com.example.finalandroid.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var recyclerViewAdapter: MovieAdapter
    var backPressedTime: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater)


        drawerLayout = findViewById(R.id.drawer)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        initRecyclerView()
        createData()

        return binding.root
    }


    private fun createData() {
        val api = API_instance.getApiInstance().create(API_service::class.java)
        val call = api.getFilms()
        call.enqueue(object : Callback<ArrayList<Film>> {
            override fun onResponse(call: Call<ArrayList<Film>>, response: Response<ArrayList<Film>>) {
                if(response.isSuccessful) {
                    val filmsList = response.body()!!
                    recyclerViewAdapter.setList(filmsList)
                    recyclerViewAdapter.setOnItemClickListener(object :
                        MovieAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
//                            Toast.makeText(this@MainActivity, "You cliсked $position", Toast.LENGTH_SHORT).show()
                            intent = Intent(this@MainActivity, filmDetails::class.java)
                            intent.putExtra("name", filmsList[position].name)
                            intent.putExtra("id", filmsList[position].id)
                            intent.putExtra("family",filmsList[position].family)
                            intent.putExtra("genus", filmsList[position].genus)
                            intent.putExtra("order", filmsList[position].order)
//                            intent.putExtra("image_url", fruitsList[position].image_url)

                            intent.putExtra(
                                "carbohydrates",
                                filmsList[position].nutritions.carbohydrates
                            )
                            intent.putExtra("protein", filmsList[position].nutritions.protein)
                            intent.putExtra("fat", filmsList[position].nutritions.fat)
                            intent.putExtra("calories", filmsList[position].nutritions.calories)
                            intent.putExtra("sugar", ffilmsList[position].nutritions.sugar)

                            startActivity(intent)
                        }
                    })

                }
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<Film>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "No internet access try again!", Toast.LENGTH_SHORT).show()
                Log.d("onFailure", t.cause.toString())
            }

        })

    }

    private fun initRecyclerView() {
        var recyclerView  = binding.recyclerView

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = MovieAdapter()
            adapter = recyclerViewAdapter
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }








    companion object {
        @JvmStatic
        fun newInstance() =Home()

    }
}