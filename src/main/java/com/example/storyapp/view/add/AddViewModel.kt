package com.example.storyapp.view.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.pref.StoryRepository
import com.example.storyapp.data.response.MessageResponse
import com.example.storyapp.data.response.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class AddViewModel(private val storyRepository: StoryRepository): ViewModel(){

    private val  _responseResult = MutableLiveData<ResultState<MessageResponse>>()
    val responseResult : MutableLiveData<ResultState<MessageResponse>> = _responseResult
    fun addStory(multipartBody: MultipartBody.Part, requestBodyDescription: RequestBody,
                 latRequestBody:RequestBody?, lonRequestBody:RequestBody?){
        viewModelScope.launch {
            try {
                _responseResult.value = ResultState.Loading
                storyRepository.getToken().collect{
                        token ->
                    if (token != null){
                         storyRepository.addStory(multipartBody, requestBodyDescription,latRequestBody,lonRequestBody)
                        _responseResult.value = ResultState.Success(MessageResponse(error = false, message = "Succes"))
                    }else{
                        _responseResult.value = ResultState.Error("Token not found")
                    }
                }
            }catch (e: HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, MessageResponse::class.java)
                responseResult. value = ResultState.Error(errorResponse.message)
            }
        }
    }
}

