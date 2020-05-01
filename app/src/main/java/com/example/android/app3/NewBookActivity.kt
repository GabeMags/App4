package com.example.android.app3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import edu.fullerton.ecs.cpsc411.restexample.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)


        // define the values from the edit text fields
        val published = findViewById<EditText>(R.id.published)
        val author = findViewById<EditText>(R.id.author)
        val title = findViewById<EditText>(R.id.title)
        val firstSentence = findViewById<EditText>(R.id.first_sentence)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:7000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener{

           if (published.text.toString() != "" && author.text.toString() != "" &&
               title.text.toString() != "" && firstSentence.text.toString() != ""){

               // You see a book. You read the label. Oh shi-- son. Is that the library's book?
               val libraryBook = Book(published.text.toString(), author.text.toString(),
                   title.text.toString(), firstSentence.text.toString())

               // You check the due date. It's due today. Better hurry before you incur a late fee.
               val call = api.insertBook(libraryBook)

               call.enqueue(object: Callback<JsonObject>{
                   override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                       Log.d("Failure", "You messed up my man")
                   }

                   override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                       Log.d("Success", "Hey. Good job.")
                   }
               })

               // Creating intent to return the data gathered
               val intent = Intent(this, MainActivity::class.java)

               Log.d("what the fuck my man", libraryBook.toString())

               // Pass the data gathered back to the main activity as a result
               intent.putExtra("published", published.text.toString())
               intent.putExtra("author", author.text.toString())
               intent.putExtra("title", title.text.toString())
               intent.putExtra("first_sentence", firstSentence.text.toString())

               /* Attempt at using serializable data -- Not quite working. */
               // intent.putExtra("library_book", library_book)

               // Set up the result and pass back the data
               setResult(Activity.RESULT_OK, intent)

               // Close current activity
               finish()
           }

           else {
               setResult(Activity.RESULT_CANCELED)
           }

        }
    }
}