package com.example.storyapp.view.story


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.MapsActivity
import com.example.storyapp.R
import com.example.storyapp.adapter.LoadingStateAdapter
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.data.response.ResultState
import com.example.storyapp.databinding.ActivityStoryBinding
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.add.AddActivity
import com.example.storyapp.view.login.LoginActivity


class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private val storyViewModel by viewModels<StoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        storyViewModel.story().observe(this) {
            story ->
            adapter.submitData(lifecycle, story)
            binding.rvStory.adapter = adapter

//            when (story){
//                is ResultState.Success -> {
//                    getData(it)
//                }
//                is ResultState.Loading -> {
//                    Log.e("loading","loading")
//                }
//                else -> {
//                    Log.e("loading","error")
//                }
//            }
        }

        binding.fab.setOnClickListener {
            val addIntent = Intent(this@StoryActivity, AddActivity::class.java)
            startActivity(addIntent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.map     -> {
                val map = Intent(this@StoryActivity, MapsActivity::class.java)
                startActivity(map)
            }
            R.id.menu_logout -> {
                storyViewModel.logout()
                val login = Intent( this@StoryActivity, LoginActivity::class.java)
                startActivity(login)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}