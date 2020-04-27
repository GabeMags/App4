package edu.fullerton.ecs.cpsc411.restexample

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/resources/books/all/")
    fun fetchAllBooks(): Call<List<Book>>
}