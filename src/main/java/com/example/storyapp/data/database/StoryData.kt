package com.example.storyapp.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "story")
@Parcelize
data class StoryData(

    val photoUrl: String,

    val name: String,

    val description: String,

    val lon: Double,

    val id: String,

    val lat: Double,
) : Parcelable
