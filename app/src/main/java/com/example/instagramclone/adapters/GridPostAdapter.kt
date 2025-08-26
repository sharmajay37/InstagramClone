package com.example.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.data.model.Post

class GridPostAdapter(private var postList: List<Post>) :
    RecyclerView.Adapter<GridPostAdapter.GridPostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridPostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_post, parent, false)
        return GridPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridPostViewHolder, position: Int) {
        val post = postList[position]
        Glide.with(holder.itemView.context)
            .load(post.imageUrl)
            .into(holder.imagePost)
    }

    override fun getItemCount(): Int = postList.size

    fun updateData(newPosts: List<Post>) {
        postList = newPosts
        notifyDataSetChanged()
    }

    class GridPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePost: ImageView = itemView.findViewById(R.id.imagePost)
    }
}
