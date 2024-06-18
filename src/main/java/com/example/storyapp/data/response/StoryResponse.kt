package com.example.storyapp.data.response

import android.content.ClipData.Item
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class StoryResponse(
    @field:SerializedName("listStory")
    val listStory: List<ListStoryItem> = emptyList(),

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
@Entity(tableName = "listStory")
@Parcelize
data class ListStoryItem(

    @field:SerializedName("photoUrl")
    @ColumnInfo("photoUrl")
    val photoUrl: String   ,

    @field:SerializedName("name")
    @ColumnInfo("name")
    val name: String   ,

    @field:SerializedName("description")
    @ColumnInfo("description")
    val description: String   ,

    @field:SerializedName("lon")
    @ColumnInfo("lon")
    val lon: Double,

    @PrimaryKey
    @field:SerializedName("id")
    @ColumnInfo("id")
    val id: String,

    @field:SerializedName("lat")
    @ColumnInfo("lat")
    val lat: Double
):Parcelable


