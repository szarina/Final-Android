package com.example.finalandroid.comment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalandroid.R
import com.example.finalandroid.databinding.CommentItemBinding
class  CommentAdapter: RecyclerView.Adapter<CommentAdapter.commentHolder>() {

    var commentList = ArrayList<CommentEntity>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    fun setList(arr: ArrayList<CommentEntity>){
        this.commentList = arr
    }

    class commentHolder(item: View, listener: onItemClickListener): RecyclerView.ViewHolder(item){
        val binding = CommentItemBinding.bind(item)
        val name = binding.username
        val comment = binding.comments

        init{
            item.setOnClickListener{
                listener.onItemClick(adapterPosition, )
            }
        }

        fun bindingComment(comment: CommentEntity){
            name.text = comment.user.username

//            comment.text = comment.film
//            comments.text = comment.content

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): commentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return commentHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: commentHolder, position: Int) {
        holder.bindingComment(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

//    fun addComment( comment: Comment){
//        commentList.add(commentList)
//    }
}