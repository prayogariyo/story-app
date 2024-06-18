package com.example.storyapp.view.detail


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.pref.StoryRepository
import com.example.storyapp.data.response.ResultState
import com.example.storyapp.data.response.Story
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val storyRepository: StoryRepository): ViewModel() {

    private var  story = MutableLiveData<ResultState<Story>>()
    val Story = story

    fun getDetailStory(id: String){
        viewModelScope.launch {
            try {
                story.value = ResultState.Loading
                storyRepository.getToken().collect{
                        token ->
                    if (token != null) {
                        val response = storyRepository.getDetailStory(id)
                        if (!response.error!!){
                            story.value = ResultState.Success(response.story!!)
                        }
                    }else{
                        story.value = ResultState.Error("Token not found")
                    }

                }
            }catch (e: HttpException){
                story.value = ResultState.Error(e.message())
            }
        }
    }
}