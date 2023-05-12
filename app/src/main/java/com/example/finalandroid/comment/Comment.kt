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
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalandroid.api.API_instance
import com.example.finalandroid.api.API_service
import com.example.finalandroid.data_classes.Rating
import com.example.finalandroid.data_classes.User
import com.example.finalandroid.databinding.ActivityCommentBinding
import com.example.finalandroid.databinding.ActivityFilmDetailsBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Comment : AppCompatActivity(){

    lateinit var binding: ActivityCommentBinding
    lateinit var recyclerViewAdapter: CommentAdapter
    lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.extras!!
        val user_id = bundle.getInt("user_id")
        val film_id = bundle.getInt("film_id")
        val username = bundle.getString("username")


        val content = binding.writeCom.text


        binding.sendCom.setOnClickListener {
            var rating = binding.ratingBar.rating
            if (username != null && content!=null && user_id != null && film_id!=null && rating!=null ) {
                writeComment(username, content.toString(), user_id,film_id,rating)
            }
        }

        initRecyclerView()
        createData(film_id)

    }
    private fun createData(film_id: Int) {



        val api = API_instance.getApiInstance().create(API_service::class.java)
        val call = api.getFilmComments(film_id)
        call.enqueue(object : Callback<ArrayList<CommentEntity>> {
            override fun onResponse(call: Call<ArrayList<CommentEntity>>, response: Response<ArrayList<CommentEntity>>) {
                if(response.isSuccessful) {
                    val commentsList = response.body()!!
                    recyclerViewAdapter.setList(commentsList)
                    recyclerViewAdapter.setOnItemClickListener(object :
                        CommentAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
//                            Toast.makeText(this@Home, "You cliсked $position", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@Comment, Comment::class.java)
                            startActivity(intent)
                        }
                    })

                }
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<CommentEntity>>, t: Throwable) {
                Toast.makeText(this@Comment, "No internet access try again!", Toast.LENGTH_SHORT).show()
                Log.d("onFailure", t.cause.toString())
            }

        })

    }
    fun writeComment(username: String, content: String, user_id:Int, film_id:Int,rating:Float) {

        val apiService = API_instance.getApiInstance().create(API_service::class.java)


        val  call = apiService.addComment(film_id,user_id, film_id, content)
        call.enqueue(object : Callback<ArrayList<CommentEntity>> {
            override fun onResponse(call: retrofit2.Call<ArrayList<CommentEntity>>, response: retrofit2.Response<ArrayList<CommentEntity>>) {

                if (response.isSuccessful == true) {
                    // Authentication successful, navigate to welcome page
                    val commentList = response.body()



                }
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<CommentEntity>>, t: Throwable) {
                // API call failed, show error message
                Toast.makeText(this@Comment, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
        recyclerViewAdapter.notifyDataSetChanged()


        val  call2 = apiService.addRating(user_id,film_id, rating)
        call2.enqueue(object : Callback<ArrayList<Rating>> {
            override fun onResponse(call: retrofit2.Call<ArrayList<Rating>>, response: retrofit2.Response<ArrayList<Rating>>) {

                if (response.isSuccessful == true) {
                    // Authentication successful, navigate to welcome page
                    val commentList = response.body()



                }
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<Rating>>, t: Throwable) {
                // API call failed, show error message
                Toast.makeText(this@Comment, "Rating already submitted or error: ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun initRecyclerView() {
        var recyclerView  = binding.recyclerComment

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Comment)
            recyclerViewAdapter = CommentAdapter()
            adapter = recyclerViewAdapter
        }
    }



//companion object{
//    @JvmStatic
//    fun newInstance()=Comment()
//}
}