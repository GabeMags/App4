package edu.fullerton.ecs.cpsc411.restexample

import android.content.Intent
import com.example.android.app3.NewBookActivity
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("api/v1/resources/books/all/")
    fun fetchAllBooks(): Call<MutableList<Book>>

    @POST("api/v1/resources/books/")
    fun insertBook(@Body book: Book):Call<Book>

}