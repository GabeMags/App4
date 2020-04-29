package edu.fullerton.ecs.cpsc411.restexample

import com.google.gson.annotations.SerializedName


class Book(val published: String, val author: String, @field:SerializedName("body") val title: String, val first_sentence: String) {
    val id: Int? = null

}