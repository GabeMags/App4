package com.example.android.app3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import edu.fullerton.ecs.cpsc411.restexample.R


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

            // define the values from the edit text fields
            val published = findViewById<EditText>(R.id.published)
            val author = findViewById<EditText>(R.id.author)
            val title = findViewById<EditText>(R.id.title)
            val firstSentence = findViewById<EditText>(R.id.first_sentence)


           if (published.text.toString() != "" && author.text.toString() != "" && title.text.toString() != "" && firstSentence.text.toString() != ""){
                // Creating intent to return the data gathered
                val intent = Intent()

                // Pass the data gathered back to the main activity as a result
                intent.putExtra("published", published.text.toString())
                intent.putExtra("author", author.text.toString())
                intent.putExtra("title", title.text.toString())
                intent.putExtra("first_sentence", firstSentence.text.toString())
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
}