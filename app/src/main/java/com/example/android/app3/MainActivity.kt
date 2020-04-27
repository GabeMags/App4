package edu.fullerton.ecs.cpsc411.restexample

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val newBookButton = findViewById<FloatingActionButton>(R.id.newBookButton)
            newBookButton.setOnClickListener{
                val intent = Intent(this, NewBookActivity::class.java)
                startActivity(intent)
            }


        // Set up Retrofit API
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:7000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.fetchAllBooks().enqueue(object : Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                showData(response.body()!!)
                d("test","First title: ${response.body()!![0].title}")
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                d("test", "Failed: ${t}")
            }
        })
    }

    private fun showData(books: List<Book>) {
        bookView.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = BookAdapter(books)
        }
    }
}
