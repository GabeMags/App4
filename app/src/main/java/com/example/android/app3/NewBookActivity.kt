package com.example.android.app3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import edu.fullerton.ecs.cpsc411.restexample.Book
import edu.fullerton.ecs.cpsc411.restexample.R
import kotlinx.android.synthetic.main.activity_new_book.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class NewBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        // POST user input to api when the submit button is clicked
        // create intent
        // any extra variables?
        // set result(result code, intent)

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener{
            // Make a POST request to /api/v1/resources/books/ with a JSON object from second activity forms
            val published: EditText = findViewById(R.id.published)
            published.text.toString().toInt()

            val author: EditText = findViewById(R.id.author)
            author.text.toString()

            val title: EditText = findViewById(R.id.title)
            title.text.toString()

            val first_sentence: EditText = findViewById(R.id.first_sentence)
            first_sentence.text.toString()

            /*
            @FormUrlEncoded
            @POST("/api/v1/resources/books/")
            fun postBook(@Body book: NewBookActivity.PostBook):Call<List<NewBookActivity.PostBook>>*/
            /*
            public void insertBook(
                @Field("published") Int published,
                @Field("author") String author,
                @Field("title") String title,
                @Field("first sentence") String first_sentence,
                Callback<Book> callback)

            // Creating intent
            val intent = Intent()

            // Putting stuff into intent
            intent.putExtra("published", published)
            intent.putExtra("author", author)
            intent.putExtra("title", title)
            intent.putExtra("first_sentence", first_sentence)

            // Set up the result
            setResult(Activity.RESULT_OK, intent)*/

            // This just closes the activity, but since we're waiting for a return of information to
            // main, and nothing is sent back, it closes the app
            finish()
        }

    }

}