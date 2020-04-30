package com.example.android.app3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import edu.fullerton.ecs.cpsc411.restexample.ApiService
import edu.fullerton.ecs.cpsc411.restexample.Book
import edu.fullerton.ecs.cpsc411.restexample.LibBook
import edu.fullerton.ecs.cpsc411.restexample.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


class NewBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)


        // define the values from the edit text fields
        val published = findViewById<EditText>(R.id.published)
        val author = findViewById<EditText>(R.id.author)
        val title = findViewById<EditText>(R.id.title)
        val first_sentence = findViewById<EditText>(R.id.first_sentence)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:7000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        // POST user input to api when the submit button is clicked
        // create intent
        // any extra variables?
        // set result(result code, intent)

        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener{

           if (published.text.toString() != "" && author.text.toString() != "" && title.text.toString() != "" && first_sentence.text.toString() != ""){

               // You see a book. Oh shit. Is that due soon?
               val library_book = LibBook(published.text.toString(), author.text.toString(), title.text.toString(), first_sentence.text.toString())

               // Run like hell, because you borrowed that from the library, never read it and it's due today
               val call = api.insertBook(library_book)

               // It doesn't matter if you intended on turning it in on time. You gotta get that bitch there.
               call.enqueue(object : Callback<LibBook>{
                   override fun onResponse(call: Call<LibBook>, response: Response<LibBook>) {
                       Log.d("test", "First title: ${response.body()}")
                   }

                   override fun onFailure(call: Call<LibBook>, t: Throwable) {
                       Log.d("test", "Failed: $t")
                   }
               })

               // Did you make it?


               // Creating intent to return the data gathered
               val intent = Intent()

               // Pass the data gathered back to the main activity as a result
               intent.putExtra("published", published.text.toString())
               intent.putExtra("author", author.text.toString())
               intent.putExtra("title", title.text.toString())
               intent.putExtra("first_sentence", first_sentence.text.toString())
               intent.putExtra("code", 200)

               // Set up the result and pass back the data
               setResult(Activity.RESULT_OK, intent)
               finish()
           }





            // Make a POST request to /api/v1/resources/books/ with a JSON object from second activity forms
            /*
            @FormUrlEncoded
            @POST("/api/v1/resources/books/")
            fun postBook(@Body book: NewBookActivity.PostBook):Call<List<NewBookActivity.PostBook>>

            public void insertBook(
                @Field("published") Int published,
                @Field("author") String author,
                @Field("title") String title,
                @Field("first sentence") String first_sentence,
                Callback<Book> callback)
            */

        }
    }

    /*public interface BookResource {
        @FormUrlEncoded
        @POST("api/v1/resources/books/")
        fun insertBook(@Body book: Book): Call<MutableList<Book>>
    }*/
}