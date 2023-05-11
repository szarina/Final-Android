package com.example.finalandroid.favorities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.Favorite
import com.example.finalandroid.databinding.FragmentFavoriteBinding
import com.example.finalandroid.filmDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteFilms : Fragment() {

    lateinit var binding:FragmentFavoriteBinding
    lateinit var  recyclerViewAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding= FragmentFavoriteBinding.inflate(inflater)
        createData()
        initRecyclerView()
        return binding.root
    }
    private fun createData() {

        val username = arguments?.getString("username")
        val user_id = arguments?.getInt("user_id", -1)
        val email = arguments?.getString("email")

        val api = API_instance.getApiInstance().create(API_service::class.java)

        val call = api.getUserFavorites(user_id!!)

        call.enqueue(object : Callback<ArrayList<Favorite>>
        { override fun onResponse(call: Call<ArrayList<Favorite>>, response: Response<ArrayList<Favorite>>) {
                if (response.isSuccessful) {

                    val favoriteList = response.body()!!
                    recyclerViewAdapter.setList(favoriteList)
                    recyclerViewAdapter.setOnItemClickListener(object :
                        FavoritesAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
//                            Toast.makeText(this@Home, "You cliсked $position", Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), filmDetails::class.java)
                            intent.putExtra("film_id", favoriteList[position].film.id)
                            intent.putExtra("user_id", favoriteList[position].user.id)
                            intent.putExtra("username", favoriteList[position].user.username)
                            intent.putExtra("description", favoriteList[position].film.description)
                            intent.putExtra("title", favoriteList[position].film.title)
                            intent.putExtra("photoLink", favoriteList[position].film.photo_link)
                            startActivity(intent)
                        }
                    })

                }
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<Favorite>>, t: Throwable) {
                Toast.makeText(requireContext(), "No internet access try again!", Toast.LENGTH_SHORT).show()
                Log.d("onFailure", t.cause.toString())
            }

        })
    }
                private fun initRecyclerView() {
                    var recyclerView  = binding.recyclerView

                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        recyclerViewAdapter = FavoritesAdapter()
                        adapter = recyclerViewAdapter
                    }
                }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFilms()

    }
}