package com.mibaldi.brewing.ui.adapters
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mibaldi.brewing.R
import com.mibaldi.brewing.utils.inflate

class MoviesAdapter(private val listener: (String) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var pubs: List<String> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_pub)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = pubs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pub: String) {
            itemView.pubTitle.text = pub
            itemView.movieCover.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
}