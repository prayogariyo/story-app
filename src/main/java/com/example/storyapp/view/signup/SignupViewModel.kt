package com.example.storyapp.view.signup


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.pref.StoryRepository
import com.example.storyapp.data.response.RegisterResponse
import com.example.storyapp.data.response.ResultState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.ResourceBundle

class SignupViewModel(private val repository: StoryRepository): ViewModel() {
    private val _responseResult = MutableLiveData<ResultState<RegisterResponse>>()
    val responseResult = _responseResult

    fun  submitRegister(name: String, email: String, password: String){
        viewModelScope.launch {
            try {
                _responseResult.value
                val response = repository.signup(name,email,password)
                if (!response.error!!){
                    _responseResult.value = ResultState.Success(response)
                }
            }catch (e:HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                _responseResult.value = ResultState.Error(errorBody?:e.message())
            }
        }
    }
}