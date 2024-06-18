package com.example.storyapp.view.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.pref.StoryRepository
import com.example.storyapp.data.pref.UserModel
import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.response.ResultState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {
    val loginResponse = MutableLiveData<ResultState<LoginResponse>>()

     fun login (email : String, password : String) {
         viewModelScope.launch {
             try {
                 val response = repository.login(email, password)
                 if (!response.error!!) {
                     Log.d("response", "${response.loginResult}")
                     loginResponse.value = ResultState.Success(LoginResponse(loginResult = response.loginResult))
                     repository.saveToken(response.loginResult?.token!!)
                 }
             }catch (e: HttpException){
                 val errorBody = e.response()?.errorBody()?.string()
                 loginResponse.value = ResultState.Error(errorBody?:e.message())
             }
         }
     }
}