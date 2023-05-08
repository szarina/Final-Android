package com.example.finalandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.databinding.MovieItemBinding

class FruitsAdapter: RecyclerView.Adapter<FruitsAdapter.fruitsHolder>() {

    var fruitsList = ArrayList<Movie>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    fun setList(arr: ArrayList<Movie>){
        this.fruitsList = arr
    }

    class fruitsHolder(item: View, listener: onItemClickListener): RecyclerView.ViewHolder(item){
        val binding = MovieItemBinding.bind(item)
        val name = binding.fruitName
        val family = binding.fruitFamily
        val order = binding.fruitOrder
        val img = binding.imageView

        init{
            item.setOnClickListener{
                listener.onItemClick(adapterPosition, )
            }
        }

        fun bindFruits(fruit: Fruit){
            name.text = fruit.name
            family.text = fruit.family
            order.text = fruit.order
            val resourceId = when (fruit.id % 5) {
                1 -> R.drawable.logo_cherry
                2 -> R.drawable.apple
                3 -> R.drawable.banana
                4->R.drawable.kiwi
                // add more cases for other fruit ids
                else -> R.drawable.orange
            }

            img.setImageResource(resourceId)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): fruitsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fruits_item, parent, false)
        return fruitsHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: fruitsHolder, position: Int) {
        holder.bindFruits(fruitsList[position])
    }

    override fun getItemCount(): Int {
        return fruitsList.size
    }
}