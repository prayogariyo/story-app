package com.example.storyapp.view.story


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.pref.StoryRepository
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.data.response.ResultState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class StoryViewModel(private val repository: StoryRepository): ViewModel() {
    val storyResponse = MutableLiveData<ResultState<List<ListStoryItem>>>()
    fun story() : LiveData<PagingData<ListStoryItem>> =
        repository.getStory().cachedIn(viewModelScope)
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}