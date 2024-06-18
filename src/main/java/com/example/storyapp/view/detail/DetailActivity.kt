package com.example.storyapp.view.detail


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.storyapp.data.response.ResultState
import com.example.storyapp.databinding.ActivityDetailBinding
import com.example.storyapp.view.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val factory = ViewModelFactory.getInstance(this)
    private val detailViewModel by viewModels<DetailViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        val id = intent.getStringExtra(EXTRA_ID)
        if(id != null) {
            Log.e("ID", "$id")
            detailViewModel.getDetailStory(id)
        }
        detailViewModel.Story.observe(this){
            story ->
            when(story){
//                is ResultState.Loading -> {
//                 binding.
//                }
                is ResultState.Success -> {
                    Log.i("story", "${story.data}")
                    binding.apply {

                        nameTextView.text = story.data.name
                        descriptionTextView.text = story.data.description

                        Glide.with(this@DetailActivity)
                            .load(story.data.photoUrl)
                            .into(photoImageView)
                    }
                }
                else -> {}
            }
        }
    }
    companion object {
        const val EXTRA_ID = "ID"
    }
}