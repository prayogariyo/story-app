package com.example.storyapp.view.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.pref.StoryRepository

class MainViewModel(private val repository: StoryRepository) : ViewModel(){
    fun getToken() = repository.getToken().asLiveData()
}