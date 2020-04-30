package edu.fullerton.ecs.cpsc411.restexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.app3.NewBookActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val requestCode = 200

    // Set up Retrofit API
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:7000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val newBookButton = findViewById<FloatingActionButton>(R.id.newBookButton)

        newBookButton.setOnClickListener{
                val intent = Intent(this, NewBookActivity::class.java)
                startActivityForResult(intent, requestCode)
            }

        api.fetchAllBooks().enqueue(object : Callback<MutableList<Book>>{
            override fun onResponse(call: Call<MutableList<Book>>, response:
            Response<MutableList<Book>>) {
                showData(response.body()!!)
                d("First Enqueue Call","Able to retrieve the first title: " +
                        response.body()!![0].title)
            }

            override fun onFailure(call: Call<MutableList<Book>>, t: Throwable) {
                d("First Enqueue Call", "Failed for the following reason: $t")
            }
        })
    }

    private fun showData(books: MutableList<Book>) {
        bookView.apply{
            adapter?.notifyDataSetChanged()

            layoutManager = LinearLayoutManager(applicationContext)

            adapter = BookAdapter(books)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data!!)

        if (resultCode == Activity.RESULT_OK && requestCode == this.requestCode)
        {
            // Extract name value from result extras
            val newPublished = data.extras!!.getString("published", null)
            val newAuthor = data.extras!!.getString("author", null)
            val newTitle = data.extras!!.getString("title", null)
            val newFirstSentence = data.extras!!.getString("first_sentence", null)

            /* Attempt at using serializable -- Not quite working. */
            // val library_book = data.extras!!.getSerializable("library_book")
            val newBook = Book(published = newPublished, author = newAuthor, title = newTitle,
                first_sentence = newFirstSentence)

            // Enqueueing to update by adding book to mutable list
            api.fetchAllBooks().enqueue(object : Callback<MutableList<Book>>{
                override fun onResponse(call: Call<MutableList<Book>>, response:
                Response<MutableList<Book>>) {
                    response.body()!!.add(newBook)
                    showData(response.body()!!)
                    d("Second Enqueue Call","Able to retrieve most recently added title:"
                            + " ${response.body()!![response.body()!!.count()-1].title}")
                }

                override fun onFailure(call: Call<MutableList<Book>>, t: Throwable) {
                    d("Second Enqueue Call", "Failed for the following reason: $t")
                }
            })

            // Toast the name to display temporarily on screen
            Toast.makeText(this, "Your book \"$newTitle\" was posted.",
                Toast.LENGTH_SHORT).show()
        }
    }
}


