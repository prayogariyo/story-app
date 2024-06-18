package com.example.storyapp.data.pref

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.data.database.StoryDatabase
import com.example.storyapp.data.database.StoryRemoteMediator
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.data.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody


class StoryRepository private constructor(
    private val apiService: ApiService,
    private val pref: UserPreference,
    private val storyDatabase: StoryDatabase
) {
    suspend fun saveToken(token: String) = pref.saveToken(token)
    fun getToken() = pref.getToken()
    suspend fun signup(name: String, email: String, password: String) =
        apiService.register(name, email, password)
     fun getStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
//                QuotePagingSource(apiService)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    suspend fun getDetailStory(id: String) = apiService.getDetailStory(id)
    suspend fun login(email: String, password: String) = apiService.login(email, password)
    suspend fun logout() = pref.logout()
    suspend fun getStoriesWithLocation() = apiService.getStoriesWithLocation()
    suspend fun addStory(
        multipartBody: MultipartBody.Part,
        requestBodyDescription: RequestBody,
        latRequestBody:RequestBody?,
        lonRequestBody: RequestBody?
    ) = apiService.uploadImage(multipartBody, requestBodyDescription, latRequestBody, lonRequestBody)


    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
            storyDatabase: StoryDatabase
        ): StoryRepository = instance ?: synchronized(this) {
            instance ?: StoryRepository(apiService, userPreference, storyDatabase )
        }.also { instance = it }
    }

}