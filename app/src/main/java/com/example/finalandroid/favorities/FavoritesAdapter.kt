package com.example.finalandroid.favorities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.R
import com.example.finalandroid.databinding.SavedFilmsItemBinding

class FavoritesAdapter(var sfilmsList:List<Favorite>, val context: Context) : RecyclerView.Adapter<FavoritesAdapter.sHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class sHolder(item: View , listener: onItemClickListener, context: Context): RecyclerView.ViewHolder(item){
        val binding = SavedFilmsItemBinding.bind(item)
        var builder = AlertDialog.Builder(context)
        val context = context

        init{
            item.setOnClickListener{
                listener.onItemClick(adapterPosition, )
            }
        }

        fun bind(movie:Favorite){
            binding.name.text = movie.name

            val resourceId = when (movie.id % 5) {
                1 -> R.drawable.dee
                2 -> R.drawable.dee
                3 -> R.drawable.dee
                4->R.drawable.dee
                // add more cases for other fruit ids
                else -> R.drawable.dee
            }
            binding.fruitImage.setImageResource(resourceId)
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_films_item, parent, false)
        return sHolder(view, mListener, context)
    }

    override fun getItemCount(): Int {
        return sfilmsList.size
    }

    override fun onBindViewHolder(holder: sHolder, position: Int) {
        holder.bind(sfilmsList[position])
        holder.deleteSavedItem(sfilmsList[position])
    }


}