package com.mibaldi.brewing.ui.adapters
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mibaldi.brewing.R
import com.mibaldi.brewing.data.model.Beer
import com.mibaldi.brewing.utils.basicDiffUtil
import com.mibaldi.brewing.utils.inflate
import kotlinx.android.synthetic.main.view_pub.view.*

class PubsAdapter(private val listener: (Beer) -> Unit) :
    RecyclerView.Adapter<PubsAdapter.ViewHolder>() {

    var beers: List<Beer> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_pub)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pub = beers[position]
        holder.bind(pub)
        holder.itemView.setOnClickListener { listener(pub) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(beer: Beer) {
            itemView.pubTitle.text = beer.title
            //itemView.movieCover.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
}