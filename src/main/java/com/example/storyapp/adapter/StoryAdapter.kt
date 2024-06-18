package com.example.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.view.detail.DetailActivity
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import com.example.storyapp.databinding.CardViewBinding

class StoryAdapter: PagingDataAdapter<ListStoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.ListViewHolder{
        val binding = CardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: StoryAdapter.ListViewHolder, position: Int) {
        val review = getItem(position)
        if (review != null){
            holder.bind(review)
        }
    }
    class ListViewHolder(private val binding: CardViewBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ListStoryItem) {
            binding.textViewCardTitle.text = "${user.name}"
            binding.textViewCardDescription.text = "${user.description}"
            Glide.with(binding.imageViewCard.context).load(user.photoUrl).into(binding.imageViewCard)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("ID", user.id)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        binding.root.context as Activity,
                        Pair(binding.imageViewCard as View, "profile"),
                        Pair(binding.textViewCardTitle as View , "name"),
                        Pair(binding.textViewCardDescription as View, "description"),
                    )
                binding.root.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}