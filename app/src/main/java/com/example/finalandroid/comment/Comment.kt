package com.example.finalandroid.comment


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.User
import com.example.finalandroid.databinding.ActivityCommentBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val username = arguments?.getString("username")
        val user_id = arguments?.getInt("user_id", -1)
        val film_id = arguments?.getInt("film_id",-1)

        val content = binding.writeCom.text

        binding.sendCom.setOnClickListener {
            if (username != null && content!=null && user_id != null && film_id!=null ) {
                writeComment(username, content.toString(), user_id,film_id)
            }
        }
        return binding.root
    }


    private fun createData() {



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
    fun writeComment(username: String, content: String, user_id:Int, film_id:Int) {

        val apiService = API_instance.getApiInstance().create(API_service::class.java)


        val  call = apiService.addComment(user_id,film_id,user_id,content)
        call.enqueue(object : Callback<ArrayList<CommentEntity>> {
            override fun onResponse(call: retrofit2.Call<ArrayList<CommentEntity>>, response: retrofit2.Response<ArrayList<CommentEntity>>) {

                if (response.isSuccessful == true) {
                    // Authentication successful, navigate to welcome page
                    val commentList = response.body()



                }
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<CommentEntity>>, t: Throwable) {
                // API call failed, show error message
                Toast.makeText(requireContext(), "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()

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
//companion object{
//    @JvmStatic
//    fun newInstance()=Comment()
//}
}