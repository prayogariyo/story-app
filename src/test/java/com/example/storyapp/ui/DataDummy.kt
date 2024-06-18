package com.example.storyapp.ui

import com.example.storyapp.data.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                i.toString(),
                "story + $i",
                "author $i",
                i.toDouble(),  "id $i", i.toDouble()
                )
            items.add(quote)
        }
        return items
    }
}