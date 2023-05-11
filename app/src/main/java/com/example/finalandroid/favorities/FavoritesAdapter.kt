package com.example.finalandroid.favorities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
    fun setList(arr: ArrayList<Favorite>){
        this.favoriteList  = arr
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class sHolder(item: View , listener: onItemClickListener): RecyclerView.ViewHolder(item){
        val binding = SavedFilmsItemBinding.bind(item)
        val name = binding.name
        val delete = binding.deleteBtn
        val img = binding.filmImage
        init{
            item.setOnClickListener{
                listener.onItemClick(adapterPosition, )
            }
        }
        fun bindingMovie(movie: Film){
            name.text = movie.title
//            description.text = movie.description
            val url = movie.photo_link
            Glide.with(img)
                .load(url)
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .fallback(R.drawable.image)
                .into(img)

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
        return sHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: sHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return  favoriteList .size
    }


}