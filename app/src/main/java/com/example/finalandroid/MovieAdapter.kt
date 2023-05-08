package com.example.finalandroid
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.data_classes.Film
import com.example.finalandroid.databinding.FilmsItemBinding
class MovieAdapter: RecyclerView.Adapter<MovieAdapter.movieHolder>() {

    var movieList = ArrayList<Film>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    fun setList(arr: ArrayList<Film>){
        this.movieList = arr
    }

    class movieHolder(item: View, listener: onItemClickListener): RecyclerView.ViewHolder(item){
        val binding = FilmsItemBinding.bind(item)
        val name = binding.nameMovie
        val description = binding.descriptionMovie
        val img = binding.imageView

        init{
            item.setOnClickListener{
                listener.onItemClick(adapterPosition, )
            }
        }

        fun bindingMovie(movie: Film){
            name.text = movie.title
            description.text = movie.description
            val resourceId = when (movie.id % 5) {
                1 -> R.drawable.dee
                2 -> R.drawable.dee
                3 -> R.drawable.dee
                4->R.drawable.dee
                // add more cases for other fruit ids
                else -> R.drawable.dee
            }

            img.setImageResource(resourceId)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.films_item, parent, false)
        return movieHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: movieHolder, position: Int) {
        holder.bindingMovie(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}