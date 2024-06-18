package com.example.storyapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storyapp.data.response.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<ListStoryItem>)
    @Query("SELECT * FROM listStory")
    fun getAllStory(): PagingSource<Int, ListStoryItem>
    @Query("DELETE FROM listStory")
    suspend fun deleteStory()
}