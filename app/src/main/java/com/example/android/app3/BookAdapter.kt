package edu.fullerton.ecs.cpsc411.restexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book_row.view.*

class BookAdapter(private val books: List<Book>) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_row, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.title
        val author: TextView = itemView.author
        val published: TextView = itemView.publisher

        init {
            itemView.setOnClickListener{
                Toast.makeText(itemView.context, books[adapterPosition].first_sentence, Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title
        holder.author.text = book.author
        holder.published.text = book.published

    }
}
