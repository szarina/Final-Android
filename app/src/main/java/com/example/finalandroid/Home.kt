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
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.Film
import com.example.finalandroid.databinding.ActivityMainBinding
import com.example.finalandroid.databinding.FragmentFavoriteBinding
import com.example.finalandroid.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : Fragment() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerViewAdapter:MovieAdapter
    var backPressedTime: Long = 0
    lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater)

        drawerLayout= findViewById(R.id.drawer2)
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
//                            Toast.makeText(this@Home, "You cliсked $position", Toast.LENGTH_SHORT).show()
                            intent = Intent(this@Home, filmDetails::class.java)
                            intent.putExtra("title", filmsList[position].title)
                            intent.putExtra("id", filmsList[position].id)
                            intent.putExtra("description",filmsList[position].description)
                            intent.putExtra("photoLink", filmsList[position].photoLink)
//                            intent.putExtra("image_url", filmsList[position].image_url)

                            startActivity(intent)
                        }
                    })

                }
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<Film>>, t: Throwable) {
                Toast.makeText(this@Home, "No internet access try again!", Toast.LENGTH_SHORT).show()
                Log.d("onFailure", t.cause.toString())
            }

        })

    }

    private fun initRecyclerView() {
        var recyclerView  = binding.recyclerView

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Home)
            recyclerViewAdapter = MovieAdapter()
            adapter = recyclerViewAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =Home()

    }
}