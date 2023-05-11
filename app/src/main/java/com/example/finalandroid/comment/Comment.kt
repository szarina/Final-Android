package com.example.finalandroid.comment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalandroid.R
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.databinding.ActivityCommentBinding

import com.example.finalandroid.databinding.CommentItemBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.zip.Inflater

class Comment : Fragment() {

    lateinit var binding: ActivityCommentBinding
    lateinit var recyclerViewAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ActivityCommentBinding.inflate(layoutInflater)
        initRecyclerView()
        createData()


        return binding.root
    }


    private fun createData() {
        val username = arguments?.getString("username")
        val id = arguments?.getInt("id", -1)
        val content = arguments?.getString("content")

        val api = API_instance.getApiInstance().create(API_service::class.java)
        val call = api.getFilmComments(1)
        call.enqueue(object : Callback<ArrayList<CommentEntity>> {
            override fun onResponse(call: Call<ArrayList<CommentEntity>>, response: Response<ArrayList<CommentEntity>>) {
                if(response.isSuccessful) {
                    val commentsList = response.body()!!
                    recyclerViewAdapter.setList(commentsList)
                    recyclerViewAdapter.setOnItemClickListener(object :
                        CommentAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
//                            Toast.makeText(this@Home, "You cliсked $position", Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), Comment::class.java)
                            startActivity(intent)
                        }
                    })

                }
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<CommentEntity>>, t: Throwable) {
                Toast.makeText(requireContext(), "No internet access try again!", Toast.LENGTH_SHORT).show()
                Log.d("onFailure", t.cause.toString())
            }

        })

    }

    private fun initRecyclerView() {
        var recyclerView  = binding.recyclerComment

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter = CommentAdapter()
            adapter = recyclerViewAdapter
        }
    }
companion object{
    @JvmStatic
    fun newInstance()=Comment()
}
}