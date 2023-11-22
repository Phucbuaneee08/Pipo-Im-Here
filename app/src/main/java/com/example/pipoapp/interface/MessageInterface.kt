package com.example.pipoapp.`interface`

import retrofit2.Call
import com.example.pipoapp.model.MessageItem
import retrofit2.http.GET

interface MessageInterface {
    @GET("posts")
    fun getData(): Call<List<MessageItem>>
}