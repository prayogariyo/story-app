package com.example.storyapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.pref.StoryRepository
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.data.response.ResultState
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: StoryRepository): ViewModel() {
    private val _storyLocation = MutableLiveData<ResultState<List<ListStoryItem>>>()
    val storyLocation: MutableLiveData<ResultState<List<ListStoryItem>>> = _storyLocation

    init {
        getStoryWithLocation()
    }
    private fun getStoryWithLocation(){
        viewModelScope.launch {
            try {
                _storyLocation.value = ResultState.Loading
                val response = repository.getStoriesWithLocation()
                if (response.listStory.isNotEmpty()){
                    _storyLocation.value = ResultState.Success(response.listStory)
                }else{
                    _storyLocation.value = ResultState.Error("Data not found")
                }
            }catch(e: Exception){
                _storyLocation.value = ResultState.Error(e.message.toString())
            }
        }
    }
}