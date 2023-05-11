package com.example.finalandroid.favorities

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.R
import com.example.finalandroid.data_classes.Favorite
import com.example.finalandroid.data_classes.Film
import com.example.finalandroid.databinding.SavedFilmsItemBinding


class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.sHolder>(){
     var favoriteList= ArrayList<Favorite>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    fun setList(arr: ArrayList<Favorite>){
        this.favoriteList  = arr
    }

    class sHolder(item: View , listener: onItemClickListener): RecyclerView.ViewHolder(item){
        val binding = SavedFilmsItemBinding.bind(item)

        init{
            item.setOnClickListener{
                listener.onItemClick(adapterPosition, )
            }
        }

        fun bind(movie:Favorite){
            binding.name.text = movie.film.title
            val resourceId = when (movie.id % 5) {
                1 -> R.drawable.dee
                2 -> R.drawable.dee
                3 -> R.drawable.dee
                4->R.drawable.dee
                // add more cases for other fruit ids
                else -> R.drawable.dee
            }
            binding.filmImage.setImageResource(resourceId)
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_films_item, parent, false)
        return sHolder(view, mListener )
    }

    override fun onBindViewHolder(holder: sHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return favoriteList .size
    }


}